package com.sugo.smart_city.api.controller;


import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "外卖红包夹接口")
@RequestMapping("/api/takeout/coupon")
public class TakeoutCouponController {

    private TakeoutCouponService takeoutCouponService;

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

}
