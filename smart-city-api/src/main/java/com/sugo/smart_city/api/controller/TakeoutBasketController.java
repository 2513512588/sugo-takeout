package com.sugo.smart_city.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.TakeoutBasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@Api(tags = "外卖购物车接口")
@RequestMapping("/api/takeout/scart")
public class TakeoutBasketController {

    @Resource
    private TakeoutBasketService takeoutScartService;

    @ApiOperation("根据用户查询购物车")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId,
                       @ParsePage Page<TakeoutBasketDto> scartPage){
       return (Result) takeoutScartService.selectPage(scartPage, userId);
    }


}
