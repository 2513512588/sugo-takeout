package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutSellerDto;
import com.sugo.smart_city.bean.enums.TakeoutSellerStatus;
import com.sugo.smart_city.bean.model.TakeoutGoodsCategory;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.model.User;
import com.sugo.smart_city.bean.vo.TakeoutSellerAddVo;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.common.valid.Groups;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.security.enums.Role;
import com.sugo.smart_city.service.TakeoutGoodsCategoryService;
import com.sugo.smart_city.service.TakeoutSellerService;
import com.sugo.smart_city.service.TakeoutSellerTypeService;
import com.sugo.smart_city.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@Api(tags = "外卖商铺接口")
@RequestMapping("/api/takeout/seller")
public class TakeoutSellerController {

    @Resource
    private TakeoutSellerService takeoutSellerService;
    @Resource
    private TakeoutGoodsCategoryService takeoutGoodsCategoryService;
    @Resource
    private TakeoutSellerTypeService takeoutSellerTypeService;

    @Resource
    private UserService userService;

    @ApiOperation("申请注册商家")
    @PostMapping("/settled/apply")
    public Result settledApply(@ParseUser Integer userId, @RequestBody @Validated(Groups.Add.class) TakeoutSellerAddVo takeoutSellerAddVo){
        TakeoutSeller isExists = takeoutSellerService.getOne(new QueryWrapper<>(TakeoutSeller.builder().userId(userId).build()));
        //判断是否提交过申请
        if (isExists != null){
            if (isExists.getStatus().equals(TakeoutSellerStatus.UNDER_REVIEW.getStatus())){
                return Result.error().message("你已提交申请了，请勿重复提交");
            }else if (isExists.getStatus().equals(TakeoutSellerStatus.FORBIDDEN.getStatus())){
                return Result.error().message("您的账户已被封禁，请联系在线客服");
            }else if (isExists.getStatus().equals(TakeoutSellerStatus.NORMAL.getStatus())){
                return Result.error().message("您的账户已正常开通店铺，请勿重复申请");
            }else {
                throw new SysException("错误的店铺状态: " + isExists);
            }
        }else {
            TakeoutSeller takeoutSeller = new TakeoutSeller();
            BeanUtils.copyProperties(takeoutSellerAddVo, takeoutSeller);
            takeoutSeller.setUserId(userId);

            // todo modify dev
            takeoutSeller.setStatus(TakeoutSellerStatus.NORMAL.getStatus());
            userService.updateById(User.builder().id(userId).roleId(Role.ROLE_TAKEOUT_SELLER.getId()).build());

            return Result.auto(takeoutSellerService.save(takeoutSeller));
        }
    }

    /**
     * 获取附近商铺
     * @param takeoutSeller 条件查询
     *  queryWrap.setProvince(takeoutSeller.getProvince());
     *  queryWrap.setCity(takeoutSeller.getCity());
     *  queryWrap.setName(takeoutSeller.getName());
     *  queryWrap.setTypeId(takeoutSeller.getTypeId());
     */
    @ApiOperation("获取附近商铺")
    @PostMapping("/list")
    public Result list(@ParsePage IPage<TakeoutSellerDto> takeoutSellerPage,
                       @RequestBody @Validated(Groups.Query.class) TakeoutSeller takeoutSeller){
        return (Result) takeoutSellerService.selectPage(takeoutSellerPage, takeoutSeller);
    }

    /**
     * 根据商家id获取商铺详情
     * @param id 商家id
     * @return 商家详情数据
     */
    @ApiOperation("根据商铺id获取商铺详情")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Integer id){
        return Result.ok().data("data", takeoutSellerService.getById(id));
    }

    /**
     * 根据商家获取店铺内的分类
     * @param sellerId 商家id
     * @return 商铺分类数据
     */
    @ApiOperation("根据商家获取店铺内的分类")
    @GetMapping("/category/{sellerId}")
    public Result categoryList(@PathVariable Integer sellerId){
        QueryWrapper<TakeoutGoodsCategory> queryWrapper = new QueryWrapper<>(TakeoutGoodsCategory.builder().sellerId(sellerId).build());
        queryWrapper.select("id", "name");
        return Result.ok().data("rows", takeoutGoodsCategoryService.list(queryWrapper));
    }

    /**
     * 获取店铺主题列表
     * @return 主题列表数据
     */
    @ApiOperation("获取店铺主题列表")
    @GetMapping("/theme/list")
    public Result themeList(){
        return Result.ok().data("rows", takeoutSellerTypeService.list());
    }

}
