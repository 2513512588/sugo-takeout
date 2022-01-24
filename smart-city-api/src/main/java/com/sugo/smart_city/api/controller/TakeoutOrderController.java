package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.enums.TakeoutSellerStatus;
import com.sugo.smart_city.bean.model.TakeoutAddress;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.param.TakeoutOrderParam;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.config.payment.AlipayConfig;
import com.sugo.smart_city.common.util.RedisUtil;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@Api(tags = "外卖订单接口")
@RequestMapping("/api/takeout/order")
public class TakeoutOrderController {

    private final TakeoutOrderService takeoutOrderService;
    private final TakeoutCouponService takeoutCouponService;
    private final TakeoutAddressService takeoutAddressService;
    private final TakeoutSellerService takeoutSellerService;

    @Autowired
    public TakeoutOrderController(TakeoutOrderService takeoutOrderService, TakeoutCouponService takeoutCouponService, TakeoutAddressService takeoutAddressService, TakeoutSellerService takeoutSellerService) {
        this.takeoutOrderService = takeoutOrderService;
        this.takeoutCouponService = takeoutCouponService;
        this.takeoutAddressService = takeoutAddressService;
        this.takeoutSellerService = takeoutSellerService;
    }

    @Autowired
    @Qualifier("alipayServiceImpl")
    private PaymentService paymentService;

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
        Object o = RedisUtil.get(orderCode);
        String payInfo = String.valueOf(o);
        return Result.ok().data("order", takeoutOrder).data("payInfo", payInfo).data("payURL", AlipayConfig.URL + "?" + payInfo);
    }


    @ApiOperation("支付成功回调")
    @PostMapping("/pay/notify")
    public String notify(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean notify = paymentService.notify(parameterMap);
        if (notify){
            TakeoutOrder takeoutOrder = new TakeoutOrder();
            takeoutOrder.setStatus(2);
            String code = request.getParameter("out_trade_no");
            takeoutOrderService.update(takeoutOrder, new QueryWrapper<>(TakeoutOrder.builder().code(code).build()));
            RedisUtil.del(code);
        }
        return notify ? "success" : "fail";
    }

    @ApiOperation("获取当前用户订单列表")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId,
                       @ParsePage IPage<TakeoutOrder> page,
                       @RequestParam(required = false) Integer[] status){
        return Result.ok().pageList(takeoutOrderService.getList(page, userId, status));
    }



}
