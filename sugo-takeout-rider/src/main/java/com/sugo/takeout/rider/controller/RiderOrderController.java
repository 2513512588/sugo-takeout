package com.sugo.takeout.rider.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.AcceptedRiderOrderDto;
import com.sugo.takeout.bean.enums.DeliveryStatus;
import com.sugo.takeout.bean.model.TakeoutDelivery;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.annotation.Delay;
import com.sugo.takeout.common.aspect.annotation.ParseLocation;
import com.sugo.takeout.common.aspect.annotation.ParsePage;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.rider.bo.NewRiderOrderBo;
import com.sugo.takeout.rider.dto.NewRiderOrderDto;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutDeliveryService;
import com.sugo.takeout.service.TakeoutOrderService;
import com.sugo.takeout.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hehaoyang
 */
@RestController
@RequestMapping("/rider/takeout/order")
@Api(tags = "骑手订单接口")
@AllArgsConstructor
@Slf4j
public class RiderOrderController {

    private TakeoutOrderService takeoutOrderService;
    private TakeoutSellerService takeoutSellerService;
    private TakeoutDeliveryService takeoutDeliveryService;
    private MapService mapService;

    @Delay
    @ApiOperation("获取新订单列表")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "status", value = "订单类型 1 等待取货 2 配送中"),
         @ApiImplicitParam(name = "location", value = "经纬度坐标")
    })
    @GetMapping("/list")
    public Result list(@ParsePage Page<?> page,
                       @RequestParam String location){
        IPage<String> iPage = takeoutOrderService.getRiderOrderCodeList(page);
        Collection<NewRiderOrderBo> values = RedisUtil.getHValues("riderOrder", Arrays.asList(iPage.getRecords().toArray()));
        for (NewRiderOrderBo newRiderOrderBo : values) {
            List<Long> longs = mapService.routematrixList(StringUtil.formatLatLngStr(location), newRiderOrderBo.getOriginLatLng() + "|" + newRiderOrderBo.getTargetLatLng());
            newRiderOrderBo.getTakeoutOrder().setOriginDistance(longs.get(0));
            newRiderOrderBo.getTakeoutOrder().setTargetDistance(longs.get(1));
        }
        List<NewRiderOrderDto> newRiderOrderDtos = values.stream().map(NewRiderOrderBo::getTakeoutOrder).collect(Collectors.toList());
        return Result.ok().pageList(iPage, newRiderOrderDtos);
    }

    @ApiOperation("获取已接订单列表")
    @GetMapping("/accepted/list")
    public Result list(@ParsePage Page<?> page,
                       @RequestParam(required = false) Integer status,
                       @ParseUser(value = Role.ROLE_TAKEOUT_RIDER) Integer riderId){
        IPage<AcceptedRiderOrderDto> iPage = takeoutOrderService.getAcceptedRiderOrderList(page, riderId, status);
        return Result.ok().pageList(iPage);
    }


    @ApiOperation("骑手接单")
    @GetMapping("/receive/{orderCode}")
    public Result receive(@ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId,
                          @PathVariable String orderCode){
        takeoutOrderService.receiveOrder(riderId, orderCode);
        RedisUtil.delHash("riderOrder", orderCode);
        return Result.ok();
    }


    @ApiOperation("订单确认取货")
    @GetMapping("/confirm-pickup/{orderCode}")
    public Result confirmPickup(@ParseLocation String location,
                                @ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId,
                                @PathVariable String orderCode){
        QueryWrapper<TakeoutOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", orderCode);
        queryWrapper.select("seller_id");
        TakeoutOrder takeoutOrder = takeoutOrderService.getOne(queryWrapper);
        QueryWrapper<TakeoutSeller> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", takeoutOrder.getSellerId());
        queryWrapper1.select("location");
        TakeoutSeller takeoutSeller = takeoutSellerService.getOne(queryWrapper1);
        String s = StringUtil.formatSellerLocation(takeoutSeller.getLocation());
        Long distance = mapService.routematrixOne(location, s);
        // 距离小于300 米可以取货
        if (distance > 300){
            QueryWrapper<TakeoutDelivery> wrapper = new QueryWrapper<>(TakeoutDelivery.builder().orderCode(orderCode).riderId(riderId).build());
            TakeoutDelivery takeoutDelivery = takeoutDeliveryService.getOne(wrapper);
            if (takeoutDelivery.getStatus() == DeliveryStatus.MEALS_HAVE_BEEN_SERVED.getStatus()){
                return Result.auto(takeoutDeliveryService.update(TakeoutDelivery.builder().status(DeliveryStatus.MEAL_TAKEN.getStatus()).build(), wrapper));
            }else if (takeoutDelivery.getStatus() == DeliveryStatus.RECEIVED_ORDER.getStatus()){
                return Result.error().message("等待商家出餐！");
            }else {
                return Result.error().message("订单状态错误！");
            }
        }else {
            return Result.error().message("当前位置离取货点较远无法取货");
        }
    }

    @ApiOperation("订单确认送达")
    @GetMapping("/confirm-arrive/{orderCode}")
    public Result confirmArrive(@ParseLocation String location,
                                @ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId,
                                @PathVariable String orderCode){
        QueryWrapper<TakeoutOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", orderCode);
        queryWrapper.select("addr_lat", "addr_lng");
        TakeoutOrder takeoutOrder = takeoutOrderService.getOne(queryWrapper);
        String s = StringUtil.formatSellerLocation(takeoutOrder.getAddrLat() + "," + takeoutOrder.getAddrLng());
        Long distance = mapService.routematrixOne(location, s);
        // 距离小于300 米可以点击送达
        if (distance > 300){
            QueryWrapper<TakeoutDelivery> wrapper = new QueryWrapper<>(TakeoutDelivery.builder().orderCode(orderCode).riderId(riderId).build());
            TakeoutDelivery takeoutDelivery = takeoutDeliveryService.getOne(wrapper);
            if (takeoutDelivery.getStatus() == DeliveryStatus.MEAL_TAKEN.getStatus()){
                return Result.auto(takeoutDeliveryService.update(TakeoutDelivery.builder().status(DeliveryStatus.DELIVERED.getStatus()).build(), wrapper));
            }else {
                return Result.error().message("订单状态错误！");
            }
        }else {
            return Result.error().message("当前位置离送货点较远无法送达");
        }
    }




}
