package com.sugo.takeout.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.GoodsListDto;
import com.sugo.takeout.bean.event.SellerEvent;
import com.sugo.takeout.bean.model.TakeoutGoods;
import com.sugo.takeout.bean.model.TakeoutGoodsCategory;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.aspect.annotation.ParsePage;
import com.sugo.takeout.common.aspect.annotation.RequestBody;
import com.sugo.takeout.common.aspect.annotation.RequestSingleParam;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.service.TakeoutGoodsCategoryService;
import com.sugo.takeout.service.TakeoutGoodsService;
import com.sugo.takeout.service.TakeoutSellerService;
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
    private TakeoutGoodsCategoryService takeoutGoodsCategoryService;

    @ApiOperation("获取推荐商品")
    @PostMapping("/near/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myLocation", value = "我的当前位置坐标（纬度,经度）"),
            @ApiImplicitParam(name = "province", value = "我的省份"),
            @ApiImplicitParam(name = "city", value = "我的城市"),
            @ApiImplicitParam(name = "type", value = "1 发现好物  2 今日特价")
    })
    @RequestBody
    public Result list(@ParsePage Page<TakeoutGoods> page,
                       @RequestSingleParam("myLocation") String myLocation,
                       @RequestSingleParam("province") String province,
                       @RequestSingleParam("city") String city,
                       @RequestSingleParam("type") Integer type){

        IPage<GoodsListDto> iPage = takeoutGoodsService.getListByCity(province, city, type, page);
        List<GoodsListDto> records = iPage.getRecords();
        List<TakeoutSeller> sellerList = new ArrayList<>();
        for (GoodsListDto record : records) {
            TakeoutSeller takeoutSeller = new TakeoutSeller();
            takeoutSeller.setLocation(record.getLocation());
            takeoutSeller.setId(record.getSellerId());
            sellerList.add(takeoutSeller);
        }

        SellerEvent sellerEvent = new SellerEvent(sellerList, StringUtil.formatLatLngStr(myLocation));
        applicationContext.publishEvent(sellerEvent);
        for (GoodsListDto takeoutGoodsDto : records) {
            takeoutGoodsDto.setAdditionalData(sellerEvent.getAdditionalData().get(takeoutGoodsDto.getSellerId()));
        }
        iPage.setRecords(records);
        return Result.ok().pageList(iPage);
    }

    @ApiOperation("根据商家id查询所有商品分类")
    @GetMapping("/category/list")
    public Result findGoodsCategoryList(@RequestParam Integer sellerId){
        List<TakeoutGoodsCategory> list = takeoutGoodsCategoryService.list(new QueryWrapper<>(TakeoutGoodsCategory.builder().sellerId(sellerId).build()));
        return Result.ok().list(list);
    }

    @ApiOperation("根据商家id和商品分类id查询商品数据")
    @GetMapping("/list")
    public Result findGoodsBySellerAndCategory(@RequestParam Integer sellerId,
                                               @RequestParam(required = false) Integer categoryId){
        return Result.ok().list(takeoutGoodsService.getListBySeller(sellerId, categoryId));
    }


}
