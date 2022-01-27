package com.sugo.takeout.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.model.TakeoutActivity;
import com.sugo.takeout.bean.model.TakeoutGoods;
import com.sugo.takeout.bean.model.TakeoutGoodsCategory;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.bean.param.TakeoutGoodsParam;
import com.sugo.takeout.bean.param.TakeoutSellerUpdateParam;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.valid.Groups;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.TakeoutActivityService;
import com.sugo.takeout.service.TakeoutGoodsCategoryService;
import com.sugo.takeout.service.TakeoutGoodsService;
import com.sugo.takeout.service.TakeoutSellerService;
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
@RequestMapping("/auth/takeout/seller")
public class TakeoutAuthSellerController {

    @Resource
    private TakeoutSellerService takeoutSellerService;
    @Resource
    private TakeoutGoodsService takeoutGoodsService;
    @Resource
    private TakeoutGoodsCategoryService takeoutGoodsCategoryService;
    @Resource
    private TakeoutActivityService takeoutGoodsCategory;

    @ApiOperation("卖家发布商品")
    @PostMapping("/goods/add")
    public Result settledApply(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId,
                               @RequestBody @Validated(Groups.Add.class) TakeoutGoodsParam takeoutGoodsVo){
         TakeoutGoods takeoutGoods = new TakeoutGoods();
         BeanUtils.copyProperties(takeoutGoodsVo, takeoutGoods);
         takeoutGoods.setSellerId(sellerId);
         return Result.auto(takeoutGoodsService.save(takeoutGoods));
    }


    @ApiOperation("卖家完善资料")
    @PostMapping("/info/edit")
    public Result editInfo(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId,
                           @RequestBody TakeoutSellerUpdateParam takeoutSellerUpdateVo){
        TakeoutSeller takeoutSeller = new TakeoutSeller();
        BeanUtils.copyProperties(takeoutSellerUpdateVo, takeoutSeller);
        takeoutSeller.setId(sellerId);

        takeoutSellerService.updateById(takeoutSeller);
        return Result.ok();
    }

    @ApiOperation("卖家完善资料")
    @PostMapping("/info/edit/dev/{sellerId}")
    public Result editInfo(@RequestBody TakeoutSellerUpdateParam takeoutSellerUpdateVo, @PathVariable Integer sellerId){
        TakeoutSeller takeoutSeller = new TakeoutSeller();
        BeanUtils.copyProperties(takeoutSellerUpdateVo, takeoutSeller);
        takeoutSeller.setId(sellerId);
        takeoutSellerService.updateById(takeoutSeller);
        return Result.ok();
    }



    /**
     * 根据商家获取店铺内的分类
     * @param sellerId 商家id
     * @return 商铺分类数据
     */
    @ApiOperation("获取当前用户店铺内的商品分类")
    @GetMapping("/category/list")
    public Result categoryList(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId){
        QueryWrapper<TakeoutGoodsCategory> queryWrapper = new QueryWrapper<>(TakeoutGoodsCategory.builder().sellerId(sellerId).build());
        queryWrapper.select("id", "name");
        return Result.ok().data("rows", takeoutGoodsCategoryService.list(queryWrapper));
    }

    /**
     * 商家添加分类数据
     */
    @ApiOperation("当前用户添加商品分类信息")
    @PostMapping("/category/add")
    public Result addCategory(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId,
                              @RequestBody TakeoutGoodsCategory takeoutGoodsCategory){
        takeoutGoodsCategory.setSellerId(sellerId);
        return Result.auto(takeoutGoodsCategoryService.save(takeoutGoodsCategory));
    }

    /**
     * 商家添加分类数据
     */
    @ApiOperation("当前用户添加优惠卷信息")
    @PostMapping("/coupon/add")
    public Result addCoupon(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId,
                            @RequestBody TakeoutActivity takeoutActivity){
        takeoutActivity.setSellerId(sellerId);
        return Result.auto(takeoutGoodsCategory.save(takeoutActivity));
    }


}
