package com.sugo.smart_city.api.controller;

import com.sugo.smart_city.bean.param.TakeoutOrderParam;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutAddressService;
import com.sugo.smart_city.service.TakeoutCouponService;
import com.sugo.smart_city.service.TakeoutOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "外卖订单接口")
@RequestMapping("/api/takeout/order")
public class TakeoutOrderController {

    private TakeoutOrderService takeoutOrderService;
    private TakeoutCouponService takeoutCouponService;
    private TakeoutAddressService takeoutAddressService;

    @ApiOperation("订单结算")
    @PostMapping("/create")
    public Result add(@ParseUser Integer userId,
                      @RequestBody @Validated TakeoutOrderParam takeoutOrderParam){

        return Result.ok();
    }


}
