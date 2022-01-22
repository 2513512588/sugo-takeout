package com.sugo.smart_city.api.controller;


import com.sugo.smart_city.bean.dto.TakeoutCouponDto;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutCouponReceiveService;
import com.sugo.smart_city.service.TakeoutCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "外卖红包接口")
@RequestMapping("/api/takeout/coupon")
public class TakeoutCouponController {

    private TakeoutCouponService takeoutCouponService;
    private TakeoutCouponReceiveService takeoutCouponReceiveService;

    @ApiOperation("领取外卖红包")
    @GetMapping("/add")
    public Result add(@ParseUser Integer userId, @RequestParam Integer couponId){
        TakeoutCoupon takeoutCoupon = takeoutCouponService.getById(couponId);
        if (takeoutCoupon != null){
            synchronized (takeoutCoupon.getId()){
                takeoutCouponService.receiveCoupon(userId, takeoutCoupon);
                return Result.ok();
            }
        }else {
            return Result.error().message("红包不存在");
        }
    }

    @ApiOperation("根据商家获取当前用户的红包")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId,
                       @RequestParam Integer sellerId){
        List<TakeoutCouponDto> list = takeoutCouponService.list(sellerId, userId);
        return Result.ok().list(list);
    }

}
