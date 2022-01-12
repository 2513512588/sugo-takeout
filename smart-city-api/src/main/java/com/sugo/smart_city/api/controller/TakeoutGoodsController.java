package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutGoodsDto;
import com.sugo.smart_city.bean.event.TakeoutSellerEvent;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.aspect.annotation.ParseParam;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.service.TakeoutGoodsService;
import com.sugo.smart_city.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "外卖商品接口")
@RequestMapping("/api/takeout/goods")
public class TakeoutGoodsController {

    private TakeoutGoodsService takeoutGoodsService;
    private TakeoutSellerService takeoutSellerService;
    private ApplicationContext applicationContext;

    @ApiOperation("获取推荐商品")
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myLocation", value = "我的当前位置坐标（纬度,经度）"),
            @ApiImplicitParam(name = "province", value = "我的省份"),
            @ApiImplicitParam(name = "city", value = "我的城市"),
            @ApiImplicitParam(name = "type", value = "1 发现好物  2 今日特价")
    })
    @ParseParam
    public Result list(@ParsePage IPage<TakeoutGoods> takeoutGoodsPage,
                       @RequestSingleParam("myLocation") String myLocation,
                       @RequestSingleParam("province") String province,
                       @RequestSingleParam("city") String city,
                       @RequestSingleParam("type") Integer type){

        String[] split = myLocation.split(",");
        IPage<TakeoutGoodsDto> iPage = takeoutGoodsService.getListByCity(province, city, type, takeoutGoodsPage);
        List<TakeoutGoodsDto> records = iPage.getRecords();
        List<TakeoutSeller> sellerList = new ArrayList<>();
        for (TakeoutGoodsDto record : records) {
            TakeoutSeller takeoutSeller = new TakeoutSeller();
            takeoutSeller.setLocation(record.getLocation());
            takeoutSeller.setId(record.getSellerId());
            sellerList.add(takeoutSeller);
        }
        String lng = String.format("%.6f", Double.parseDouble(split[0]));
        String lat = String.format("%.6f", Double.parseDouble(split[1]));
        TakeoutSellerEvent takeoutSellerEvent = new TakeoutSellerEvent(sellerList, String.format("%s,%s", lat, lng));
        applicationContext.publishEvent(takeoutSellerEvent);
        for (TakeoutGoodsDto takeoutGoodsDto : records) {
            takeoutGoodsDto.setAdditionalData(takeoutSellerEvent.getAdditionalData().get(takeoutGoodsDto.getSellerId()));
        }
        iPage.setRecords(records);
        return Result.ok().pageList(iPage);
    }

    @ApiOperation("根据商家id查询商品数据")
    @GetMapping("/seller/{sellerId}")
    public Result findGoodsBySeller(@PathVariable Integer sellerId){
        TakeoutGoods takeoutGoods = new TakeoutGoods();
        takeoutGoods.setSellerId(sellerId);
        QueryWrapper<TakeoutGoods> queryWrapper = new QueryWrapper<>(takeoutGoods);
        return Result.ok().list(takeoutGoodsService.list(queryWrapper));
    }

    @ApiOperation("根据商家id和商品分类id查询商品数据")
    @GetMapping("/seller/{sellerId}/category/{categoryId}")
    public Result findGoodsBySellerAndCategory(@PathVariable Integer sellerId,
                                               @PathVariable Integer categoryId){
        TakeoutGoods takeoutGoods = new TakeoutGoods();
        takeoutGoods.setSellerId(sellerId);
        takeoutGoods.setCategoryId(categoryId);
        QueryWrapper<TakeoutGoods> queryWrapper = new QueryWrapper<>(takeoutGoods);
        return Result.ok().list(takeoutGoodsService.list(queryWrapper));
    }


}
