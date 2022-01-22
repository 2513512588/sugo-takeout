package com.sugo.smart_city.api.controller;

import com.sugo.smart_city.bean.model.TakeoutActivity;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.service.TakeoutActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = "外卖活动接口")
@RequestMapping("/api/takeout/activity")
public class TakeoutActivityController {

    private TakeoutActivityService takeoutActivityService;

    @ApiOperation("获取指定商铺的活动列表")
    @RequestMapping("/list")
    public Result list(@RequestParam Integer sellerId){
        List<TakeoutActivity> list = takeoutActivityService.list(sellerId);
        return Result.ok().list(list);
    }


}
