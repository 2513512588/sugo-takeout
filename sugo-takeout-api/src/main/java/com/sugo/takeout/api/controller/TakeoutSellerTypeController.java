package com.sugo.takeout.api.controller;

import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.service.TakeoutSellerTypeService;
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
@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = "外卖店铺类型接口")
@RequestMapping("/api/takeout/seller/type")
public class TakeoutSellerTypeController {

    private TakeoutSellerTypeService takeoutSellerTypeService;

    @ApiOperation("获取店铺类型列表")
    @GetMapping("/list")
    public Result list(){
        return Result.ok().list(takeoutSellerTypeService.list());
    }


}
