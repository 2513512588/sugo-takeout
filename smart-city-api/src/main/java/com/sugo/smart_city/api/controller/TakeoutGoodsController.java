package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.service.TakeoutGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * todo 商品位置
 */
@Slf4j
@RestController
@Api(tags = "外卖商品接口")
@RequestMapping("/api/takeout/goods")
public class TakeoutGoodsController {

    @Resource
    private TakeoutGoodsService takeoutGoodsService;

    @ApiOperation("获取推荐商品")
    @GetMapping("/list")
    public Result list(){

        return Result.ok();
    }

    @ApiOperation("根据商家id查询商品数据")
    @GetMapping("/seller/{sellerId}")
    public Result findGoodsBySeller(@PathVariable Integer sellerId){
        TakeoutGoods takeoutGoods = new TakeoutGoods();
        takeoutGoods.setSellerId(sellerId);
        QueryWrapper<TakeoutGoods> queryWrapper = new QueryWrapper<>(takeoutGoods);
        return Result.ok().data("rows", takeoutGoodsService.list(queryWrapper));
    }

    @ApiOperation("根据商家id和商品分类id查询商品数据")
    @GetMapping("/seller/{sellerId}/category/{categoryId}")
    public Result findGoodsBySellerAndCategory(@PathVariable Integer sellerId,
                                               @PathVariable Integer categoryId){
        TakeoutGoods takeoutGoods = new TakeoutGoods();
        takeoutGoods.setSellerId(sellerId);
        takeoutGoods.setCategoryId(categoryId);
        QueryWrapper<TakeoutGoods> queryWrapper = new QueryWrapper<>(takeoutGoods);
        return Result.ok().data("rows", takeoutGoodsService.list(queryWrapper));
    }

}
