package com.sugo.takeout.rider.controller;


import com.sugo.takeout.common.annotation.Delay;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.rider.dto.TakeoutOrderDto;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hehaoyang
 */
@RestController
@RequestMapping("/rider/takeout/order")
@Api(tags = "骑手订单接口")
@AllArgsConstructor
public class RiderOrderController {

    private TakeoutOrderService takeoutOrderService;
    private MapService mapService;

    @Delay
    @ApiOperation("获取订单列表")
    @GetMapping("/list")
    public Result list(@RequestParam String location){
        List<TakeoutOrderDto> list = RedisUtil.get("riderOrder");
        for (TakeoutOrderDto takeoutOrderDto : list) {
            List<Long> longs = mapService.routematrixList(StringUtil.formatLatLngStr(location), takeoutOrderDto.getOriginLatLng() + "|" + takeoutOrderDto.getTargetLatLng());
            takeoutOrderDto.setOriginDistance(longs.get(0));
            takeoutOrderDto.setTargetDistance(longs.get(1));
        }
        return Result.ok().list(list);
    }


    @ApiOperation("骑手接单")
    @GetMapping("/receive/{orderCode}")
    public Result receive(@ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId,
                          @PathVariable String orderCode){

        return Result.ok();
    }


}
