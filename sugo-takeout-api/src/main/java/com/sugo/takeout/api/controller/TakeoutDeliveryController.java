package com.sugo.takeout.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.enums.DeliveryStatus;
import com.sugo.takeout.bean.enums.OrderStatus;
import com.sugo.takeout.bean.enums.RedisKey;
import com.sugo.takeout.bean.model.TakeoutDelivery;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.annotation.Delay;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutDeliveryService;
import com.sugo.takeout.service.TakeoutOrderService;
import com.sugo.takeout.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@Api(tags = "外卖订单物流接口")
@RequestMapping("/api/takeout/delivery")
@AllArgsConstructor
public class TakeoutDeliveryController {

    private TakeoutDeliveryService takeoutDeliveryService;
    private TakeoutOrderService takeoutOrderService;
    private MapService mapService;
    private TakeoutSellerService takeoutSellerService;

    @Delay
    @ApiOperation("获取订单物流状态")
    @GetMapping("/real-status")
    public Result riderLocation(@RequestParam String orderCode,
                                @ParseUser Integer userId){
        TakeoutDelivery takeoutDelivery = takeoutDeliveryService.getLastDeliveryByOrderCodeAndUserId(orderCode, userId);
        QueryWrapper<TakeoutOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", orderCode);
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("status", "rider_id", "addr_lat", "addr_lng", "seller_id");
        TakeoutOrder takeoutOrder = takeoutOrderService.getOne(queryWrapper);
        if (takeoutOrder != null){
            if (takeoutOrder.getStatus() == OrderStatus.PAID.getStatus()){
                Result result = Result.ok().data("delivery", takeoutDelivery);
                if (takeoutOrder.getRiderId() != null){
                    String location = RedisUtil.getHValue(RedisKey.RIDER_LOCATION.getName(), takeoutOrder.getRiderId());
                    result.data("riderLocation", location);
                    if (!StringUtils.isEmpty(location)){
                        StringUtil.validLatLng(location);
                        // 已取餐
                        if (takeoutDelivery.getRiderStatus() == DeliveryStatus.MEAL_TAKEN.getStatus()){
                            long[] longs = mapService.routeMatrixOne(location, takeoutOrder.getAddrLat() + "," + takeoutOrder.getAddrLng());
                            result.data("distance", longs[0]);
                            result.data("duration", longs[1]);
                            result.data("arriveTime", LocalTime.now().plusMinutes(longs[1]));
                            // 未取餐
                        }else if (takeoutDelivery.getRiderStatus() == DeliveryStatus.RECEIVED_ORDER.getStatus()){
                            QueryWrapper<TakeoutSeller> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.select("location");
                            queryWrapper1.eq("id", takeoutOrder.getSellerId());
                            TakeoutSeller takeoutSeller = takeoutSellerService.getOne(queryWrapper1);
                            String[] desc = new String[]{takeoutOrder.getAddrLat() + "," + takeoutOrder.getAddrLng(), StringUtil.parseSellerLocation(takeoutSeller.getLocation())};
                            List<long[]> list = mapService.routeMatrix(location, org.apache.commons.lang3.StringUtils.join(desc, "|"));
                            result.data("distance", list.get(0)[0] + list.get(1)[0]);
                            long duration = list.get(0)[1] + list.get(1)[1];
                            result.data("duration", duration);
                            result.data("arriveTime", LocalTime.now().plusMinutes(duration));
                        }
                    }
                }
                return result;
            }else {
                return Result.error().message("订单状态异常");
            }
        }else {
            return Result.error().message("订单不存在");
        }
    }

}
