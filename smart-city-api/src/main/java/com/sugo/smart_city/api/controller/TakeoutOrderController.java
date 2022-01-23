package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.smart_city.bean.enums.TakeoutSellerStatus;
import com.sugo.smart_city.bean.model.TakeoutAddress;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.param.TakeoutOrderParam;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutAddressService;
import com.sugo.smart_city.service.TakeoutCouponService;
import com.sugo.smart_city.service.TakeoutOrderService;
import com.sugo.smart_city.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private TakeoutSellerService takeoutSellerService;

    @ApiOperation("订单结算")
    @PostMapping("/create")
    public Result add(@ParseUser Integer userId,
                      @RequestBody @Validated TakeoutOrderParam takeoutOrderParam) {
        TakeoutCoupon takeoutCoupon = null;
        if (takeoutOrderParam.getCouponId() != null){
            takeoutCoupon = takeoutCouponService.getByIdAndUser(takeoutOrderParam.getCouponId(), userId);
            if (takeoutCoupon == null) {
                return Result.error().message("优惠卷不存在或已过期");
            }
        }
        TakeoutAddress takeoutAddress = takeoutAddressService.getOne(new QueryWrapper<>(TakeoutAddress.builder().id(takeoutOrderParam.getAddrId()).build()));
        if (takeoutAddress == null) {
            return Result.error().message("收货地址不存在");
        }
        TakeoutSeller takeoutSeller = takeoutSellerService.getById(takeoutOrderParam.getSellerId());
        if (takeoutSeller == null) {
            return Result.error().message("商家不存在");
        } else {
            if (takeoutSeller.getStatus() != TakeoutSellerStatus.NORMAL.getStatus()) {
                return Result.error().message("店铺尚未开通或已封禁");
            }
        }
        synchronized (userId){
            String orderNo = takeoutOrderService.createOrder(takeoutCoupon, takeoutAddress, takeoutSeller, takeoutOrderParam, userId);
            return Result.ok().data("orderNo", orderNo);
        }
    }

    @ApiOperation("通过订单编号获取订单信息")
    @GetMapping("/detail/{orderCode}")
    public Result detail(@ParseUser Integer userId, @PathVariable String orderCode){
        TakeoutOrder takeoutOrder = takeoutOrderService.getOne(new QueryWrapper<>(TakeoutOrder.builder().code(orderCode).userId(userId).build()));
        return Result.ok().data(takeoutOrder);
    }


}
