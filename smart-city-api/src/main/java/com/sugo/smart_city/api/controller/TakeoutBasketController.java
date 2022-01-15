package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import com.sugo.smart_city.bean.param.TakeoutBasketParam;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.common.valid.Groups;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
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
                       @ParsePage Page<TakeoutBasketDto> basketPage){
       return Result.ok().pageList(takeoutBasketService.selectPage(basketPage, userId));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id"),
            @ApiImplicitParam(name = "quantity", value = "加购数量（为0时将移除此购物车数据）"),
            @ApiImplicitParam(name = "skuId", value = "商品规格id"),
    })
    @ApiOperation("修改购物车商品数量")
    @PostMapping("/update")
    public Result update(@ParseUser Integer userId,
                         @RequestBody @Validated(Groups.Add.class) TakeoutBasketParam takeoutBasketParam){
        return Result.auto(takeoutBasketService.updateQuantity(userId, takeoutBasketParam));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id"),
            @ApiImplicitParam(name = "skuId", value = "商品规格id"),
    })
    @ApiOperation("查询当前用户某个商品的加购详情")
    @PostMapping("/detail")
    public Result detail(@ParseUser Integer userId,
                         @RequestBody @Validated(Groups.Query.class) TakeoutBasketParam takeoutBasketParam){
        TakeoutBasket takeoutBasket = mapperFacade.map(takeoutBasketParam, TakeoutBasket.class);
        takeoutBasket.setUserId(userId);
        return Result.ok().data(takeoutBasketService.getOne(new QueryWrapper<>(takeoutBasket)));
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




}
