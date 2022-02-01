package com.sugo.takeout.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.BasketDto;
import com.sugo.takeout.bean.enums.GoodsStatus;
import com.sugo.takeout.bean.model.TakeoutBasket;
import com.sugo.takeout.bean.param.BasketParam;
import com.sugo.takeout.common.aspect.annotation.ParsePage;
import com.sugo.takeout.common.aspect.annotation.RequestBody;
import com.sugo.takeout.common.aspect.annotation.RequestSingleParam;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.valid.Groups;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.service.TakeoutBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * @author hehaoyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = "外卖购物车接口")
@RequestMapping("/api/takeout/basket")
public class TakeoutBasketController {

    private TakeoutBasketService takeoutBasketService;
    private final MapperFacade mapperFacade;

    @ApiOperation("根据用户查询购物车")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId,
                       @ParsePage Page<BasketDto> basketPage){
       return Result.ok().pageList(takeoutBasketService.selectPage(basketPage, userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "店铺id"),
    })
    @ApiOperation("查询当前用户某店铺的购物车数据")
    @GetMapping("/listBySeller")
    public Result list(@ParseUser(required = false) Integer userId, @RequestParam Integer sellerId,
                       @RequestParam(required = false) Integer status){
         if (StringUtils.isEmpty(status)){
             status = GoodsStatus.ON_SHELF.getStatus();
         }
         if (userId != null){
             return Result.ok().list(takeoutBasketService.list(userId, sellerId, status));
         }else {
             return Result.ok();
         }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id"),
            @ApiImplicitParam(name = "quantity", value = "加购数量（为0时将移除此购物车数据）"),
            @ApiImplicitParam(name = "skuId", value = "商品规格id"),
    })
    @ApiOperation("修改购物车商品数量")
    @PostMapping("/update")
    public Result update(@ParseUser Integer userId,
                         @org.springframework.web.bind.annotation.RequestBody @Validated(Groups.Add.class) BasketParam basketParam)  {
        return Result.auto(takeoutBasketService.updateQuantity(userId, basketParam));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id"),
            @ApiImplicitParam(name = "skuId", value = "商品规格id"),
    })
    @ApiOperation("查询当前用户某个商品的加购详情")
    @PostMapping("/detail")
    public Result detail(@ParseUser(required = false) Integer userId,
                         @org.springframework.web.bind.annotation.RequestBody @Validated(Groups.Query.class) BasketParam basketParam){
        if (userId != null){
            TakeoutBasket takeoutBasket = mapperFacade.map(basketParam, TakeoutBasket.class);
            takeoutBasket.setUserId(userId);
            return Result.ok().data(takeoutBasketService.getOne(new QueryWrapper<>(takeoutBasket)));
        }else {
            return Result.ok();
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "购物车id", required = true, dataType = "Integer")
    })
    @ApiOperation("根据购物车id删除购物车数据")
    @DeleteMapping("/del/{id}")
    public Result deleteById(@ParseUser Integer userId,
                             @PathVariable Integer id){
        return Result.auto(takeoutBasketService.remove(new QueryWrapper<>(TakeoutBasket.builder().id(id).userId(userId).build())));
    }


    /**
     * 清空购物车
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "商家id", required = true, dataType = "Integer")
    })
    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    @RequestBody
    public Result clear(@ParseUser Integer userId, @RequestSingleParam("sellerId") Integer sellerId){
        QueryWrapper<TakeoutBasket> queryWrapper = new QueryWrapper<>(TakeoutBasket.builder().sellerId(sellerId).userId(userId).build());
        return Result.auto(takeoutBasketService.remove(queryWrapper));
    }




}
