package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutSellerDto;
import com.sugo.smart_city.bean.enums.TakeoutSellerStatus;
import com.sugo.smart_city.bean.event.TakeoutSellerEvent;
import com.sugo.smart_city.bean.model.TakeoutGoodsCategory;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.model.User;
import com.sugo.smart_city.bean.param.TakeoutSellerAddParam;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.aspect.annotation.ParseParam;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.common.valid.Groups;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.security.enums.Role;
import com.sugo.smart_city.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private LocationService locationService;

    @Resource
    private UserService userService;

    @Resource
    private ApplicationContext applicationContext;

    @ApiOperation("申请注册商家")
    @PostMapping("/settled/apply")
    public Result settledApply(@ParseUser User user, @RequestBody @Validated(Groups.Add.class) TakeoutSellerAddParam takeoutSellerAddVo){
        TakeoutSeller isExists = takeoutSellerService.getOne(new QueryWrapper<>(TakeoutSeller.builder().userId(user.getId()).build()));
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
            takeoutSeller.setUserId(user.getId());
            takeoutSeller.setPhone(user.getPhone());

            // todo modify dev
            takeoutSeller.setStatus(TakeoutSellerStatus.NORMAL.getStatus());
            userService.updateById(User.builder().id(user.getId()).roleId(Role.ROLE_TAKEOUT_SELLER.getId()).build());

            return Result.auto(takeoutSellerService.save(takeoutSeller));
        }
    }

    /**
     * 获取附近商铺
     *  queryWrap.setProvince(takeoutSeller.getProvince());
     *  queryWrap.setCity(takeoutSeller.getCity());
     *  queryWrap.setName(takeoutSeller.getName());
     *  queryWrap.setTypeId(takeoutSeller.getTypeId());
     */
    @ApiOperation("获取附近商铺")
    @PostMapping("/list")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "myLocation", value = "我的当前位置坐标（纬度,经度）"),
         @ApiImplicitParam(name = "province", value = "我的省份"),
         @ApiImplicitParam(name = "city", value = "我的城市"),
    })
    @ParseParam
    public Result list(@ParsePage IPage<TakeoutSeller> takeoutSellerPage,
                       @RequestSingleParam("myLocation") String myLocation,
                       @RequestSingleParam("province") String province,
                       @RequestSingleParam("city") String city){
        IPage<TakeoutSeller> iPage = takeoutSellerService.getBaseMapper().selectPage(takeoutSellerPage,
                new QueryWrapper<>(TakeoutSeller.builder().province(province).city(city).build()));
        List<TakeoutSeller> records = iPage.getRecords();
        List<TakeoutSellerDto> takeoutSellerDtos = new ArrayList<>();
        String[] split = myLocation.split(",");
        String lng = String.format("%.6f", Double.parseDouble(split[0]));
        String lat = String.format("%.6f", Double.parseDouble(split[1]));
        TakeoutSellerEvent takeoutSellerEvent = new TakeoutSellerEvent(records, String.format("%s,%s", lat, lng));
        applicationContext.publishEvent(takeoutSellerEvent);
        for (TakeoutSeller record : records) {
            TakeoutSellerDto takeoutSellerDto = new TakeoutSellerDto();
            BeanUtils.copyProperties(record, takeoutSellerDto);
            takeoutSellerDto.setAdditionalData(takeoutSellerEvent.getAdditionalData().get(record.getId()));
            takeoutSellerDtos.add(takeoutSellerDto);
        }
        return Result.ok().pageList(iPage, takeoutSellerDtos);
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
