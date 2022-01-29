package com.sugo.takeout.seller.controller;

import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehaoyang
 */
@RestController
@RequestMapping("/seller/takeout/order")
@Api(tags = "商家订单接口")
@AllArgsConstructor
@Slf4j
public class OrderController {

    @ApiOperation("商家出餐")
    @GetMapping("/eat-out/{orderCode}")
    public Result eatOut(@ParseUser(Role.ROLE_TAKEOUT_SELLER)Integer sellerId,
                         @PathVariable Integer orderCode){

        return Result.ok();
    }

}
