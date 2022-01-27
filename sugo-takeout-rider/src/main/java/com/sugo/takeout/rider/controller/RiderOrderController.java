package com.sugo.takeout.rider.controller;


import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.common.annotation.Delay;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.TakeoutOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Delay
    @ApiOperation("获取订单列表")
    @GetMapping("/list")
    public Result list(){
        List<TakeoutOrder> list = RedisUtil.get("riderOrder");
        return Result.ok();
    }


    @ApiOperation("骑手接单")
    @GetMapping("/receive/{orderCode}")
    public Result receive(@ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId,
                          @PathVariable String orderCode){

        return Result.ok();
    }


}
