package com.sugo.takeout.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.OrderDetailDto;
import com.sugo.takeout.bean.enums.OrderStatus;
import com.sugo.takeout.bean.enums.SellerStatus;
import com.sugo.takeout.bean.model.TakeoutAddress;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.bean.param.OrderParam;
import com.sugo.takeout.common.aspect.annotation.ParsePage;
import com.sugo.takeout.common.config.payment.AlipayConfig;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.service.*;
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
    private final AlipayConfig alipayConfig;

    @Autowired
    public TakeoutOrderController(TakeoutOrderService takeoutOrderService, TakeoutCouponService takeoutCouponService, TakeoutAddressService takeoutAddressService, TakeoutSellerService takeoutSellerService, AlipayConfig alipayConfig) {
        this.takeoutOrderService = takeoutOrderService;
        this.takeoutCouponService = takeoutCouponService;
        this.takeoutAddressService = takeoutAddressService;
        this.takeoutSellerService = takeoutSellerService;
        this.alipayConfig = alipayConfig;
    }

    @Autowired
    @Qualifier("alipayServiceImpl")
    private PaymentService paymentService;

    @ApiOperation("订单结算")
    @PostMapping("/create")
    public Result add(@ParseUser Integer userId,
                      @RequestBody @Validated OrderParam orderParam) {
        TakeoutCoupon takeoutCoupon = null;
        if (orderParam.getCouponId() != null){
            takeoutCoupon = takeoutCouponService.getByIdAndUser(orderParam.getCouponId(), userId);
            if (takeoutCoupon == null) {
                return Result.error().message("优惠卷不存在或已过期");
            }
        }
        TakeoutAddress takeoutAddress = takeoutAddressService.getOne(new QueryWrapper<>(TakeoutAddress.builder().id(orderParam.getAddrId()).build()));
        if (takeoutAddress == null) {
            return Result.error().message("收货地址不存在");
        }
        TakeoutSeller takeoutSeller = takeoutSellerService.getById(orderParam.getSellerId());
        if (takeoutSeller == null) {
            return Result.error().message("商家不存在");
        } else {
            if (takeoutSeller.getStatus() != SellerStatus.NORMAL.getStatus()) {
                return Result.error().message("店铺尚未开通或已封禁");
            }
        }
        synchronized (userId){
            String orderNo = takeoutOrderService.createOrder(takeoutCoupon, takeoutAddress, takeoutSeller, orderParam, userId);
            return Result.ok().data("orderNo", orderNo);
        }
    }

    @ApiOperation("通过订单编号获取订单信息")
    @GetMapping("/detail/{orderCode}")
    public Result detail(@ParseUser Integer userId, @PathVariable String orderCode){
        OrderDetailDto takeoutOrder = takeoutOrderService.getDetail(userId, orderCode);
        return Result.ok().data(takeoutOrder);
    }

    @ApiOperation("通过订单编号获取支付信息")
    @GetMapping("/payment/detail/{orderCode}")
    public Result paymentInfo(@ParseUser Integer userId, @PathVariable String orderCode){
        OrderDetailDto takeoutOrder = takeoutOrderService.getDetail(userId, orderCode);
        Object o = RedisUtil.get(orderCode);
        String payInfo = String.valueOf(o);
        System.out.println(payInfo);
        return Result.ok().data("order", takeoutOrder).data("payInfo", payInfo).data("payURL", alipayConfig.baseUrl + "?" + payInfo);
    }


    @ApiOperation("支付成功回调")
    @PostMapping("/pay/notify")
    public String notify(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean notify = paymentService.notify(parameterMap);
        if (notify){
            String code = request.getParameter("out_trade_no");
            takeoutOrderService.paySucess(code);
            RedisUtil.del(code);
        }
        return notify ? "success" : "fail";
    }

    @ApiOperation("获取当前用户订单列表")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId,
                       @ParsePage Page<TakeoutOrder> page,
                       @RequestParam(required = false) Integer[] status){
        return Result.ok().pageList(takeoutOrderService.getList(page, userId, status));
    }


    @ApiOperation("获取订单的商家位置信息和收货地址位置信息")
    @GetMapping("/map/info")
    public Result mapInfo(@RequestParam String code){
        QueryWrapper<TakeoutOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.select("addr_lat", "addr_lng", "seller_id", "status");
        TakeoutOrder takeoutOrder = takeoutOrderService.getOne(queryWrapper);
        if (takeoutOrder != null){
            if (takeoutOrder.getStatus() == OrderStatus.PAID.getStatus()){
                QueryWrapper<TakeoutSeller> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", takeoutOrder.getSellerId());
                queryWrapper1.select("location", "name", "avatar");
                TakeoutSeller takeoutSeller = takeoutSellerService.getOne(queryWrapper1);
                String location = StringUtil.parseSellerLocation(takeoutSeller.getLocation());
                return Result.ok().data("sellerLocation", location.split(","))
                                  .data("customLocation", new String[]{takeoutOrder.getAddrLat(), takeoutOrder.getAddrLng()})
                                  .data("sellerName", takeoutSeller.getName())
                                  .data("sellerLogo", takeoutSeller.getAvatar());
            }else {
                return Result.error().message("订单状态不正确");
            }
        }else {
            return Result.error().message("订单不存在");
        }
    }


}
