package com.sugo.takeout.seller.controller;


import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehaoyang
 */
@RestController
@RequestMapping("/seller/takeout")
@Api(tags = "商家信息接口")
@AllArgsConstructor
@Slf4j
public class SellerController {

    private TakeoutSellerService takeoutSellerService;

    @ApiOperation("获取当前商家账号的店铺经营数据")
    @GetMapping("/total-data")
    public Result totalData(@ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId){
        return Result.ok().data(takeoutSellerService.getDataById(sellerId));
    }

}
