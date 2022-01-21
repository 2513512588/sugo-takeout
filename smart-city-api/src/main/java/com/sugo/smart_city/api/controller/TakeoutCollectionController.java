package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.smart_city.bean.model.TakeoutCollection;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.common.aspect.annotation.ParseParam;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "外卖收藏夹接口")
@RequestMapping("/api/takeout/collection")
public class TakeoutCollectionController {

    private TakeoutCollectionService takeoutCollectionService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "卖家id", required = true),
            @ApiImplicitParam(name = "goodsId", value = "商品Id")
    })
    @ApiOperation("添加店铺或者商品收藏")
    @PostMapping("/add")
    @ParseParam
    public Result add(@ParseUser Integer userId,
                      @RequestSingleParam("sellerId") Integer sellerId,
                      @RequestSingleParam(value = "goodsId", required = false) Integer goodsId){
        TakeoutCollection build = TakeoutCollection.builder().userId(userId).sellerId(sellerId).goodsId(goodsId).build();
        try {
            return Result.auto(takeoutCollectionService.save(build)).data("collected", true);
        }catch (DuplicateKeyException e){
            return Result.error().message("你已收藏过了");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "卖家id", required = true),
            @ApiImplicitParam(name = "goodsId", value = "商品Id")
    })
    @ApiOperation("取消收藏")
    @DeleteMapping("/del")
    public Result del(@ParseUser Integer userId,
                      @RequestSingleParam("sellerId") Integer sellerId,
                      @RequestSingleParam(value = "goodsId", required = false) Integer goodsId){
        TakeoutCollection build = TakeoutCollection.builder().userId(userId).sellerId(sellerId).goodsId(goodsId).build();
        boolean remove = takeoutCollectionService.remove(new QueryWrapper<>(build));
        if (remove){
            return Result.ok().data("collected", false);
        }else {
            return Result.error().message("你还未收藏");
        }
    }

    @ApiOperation("获取收藏商家列表")
    @GetMapping("/list")
    public Result sellerList(@ParseUser Integer userId){
        //todo 连接收藏商家信息列表
        List<TakeoutGoods> goodsList = takeoutCollectionService.getCollectSellerList(userId);
        return Result.ok();
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "卖家id"),
    })
    @ApiOperation("获取收藏商品列表")
    @GetMapping("/goods/list")
    public Result goodsList(@ParseUser Integer userId,
                            @RequestParam(required = false) Integer sellerId){
        //todo 连接收藏的商品信息列表
        List<TakeoutGoods> goodsList = takeoutCollectionService.getCollectSellerList(userId);
        return Result.ok();
    }


    @ApiImplicitParams({
         @ApiImplicitParam(name = "sellerId", value = "卖家id", required = true),
         @ApiImplicitParam(name = "goodsId", value = "商品Id")
    })
    @ApiOperation("判断是否收藏了")
    @GetMapping("/check")
    public Result check(@ParseUser Integer userId,
                        @RequestParam Integer sellerId,
                        @RequestParam(required = false) Integer goodsId){
        return Result.ok().data("check", takeoutCollectionService.exists(userId, sellerId, goodsId));
    }




}
