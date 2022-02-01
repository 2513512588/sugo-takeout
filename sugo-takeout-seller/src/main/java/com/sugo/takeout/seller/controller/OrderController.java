package com.sugo.takeout.seller.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.SellerOrderDto;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.common.aspect.annotation.ParsePage;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.service.TakeoutOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author hehaoyang
 */
@RestController
@RequestMapping("/seller/takeout/order")
@Api(tags = "商家订单接口")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private TakeoutOrderService takeoutOrderService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "1 进行中（已出餐之后） 2 新订单（待支付）3 退款中 4 待出餐")
    })
    @ApiOperation("获取商家订单")
    @GetMapping("/list")
    public Result orderList(@ParsePage Page<TakeoutOrder> page,
                            @ParseUser(Role.ROLE_TAKEOUT_SELLER) Integer sellerId,
                            @RequestParam Integer status){
        IPage<SellerOrderDto> iPage = takeoutOrderService.getSellerOrderList(page, sellerId, status);
        return Result.ok().pageList(iPage);
    }

    @ApiOperation("商家出餐")
    @GetMapping("/eat-out/{orderCode}")
    public Result eatOut(@ParseUser(Role.ROLE_TAKEOUT_SELLER)Integer sellerId,
                         @PathVariable Integer orderCode){

        return Result.ok();
    }


}
