package com.sugo.smart_city.api.controller.common;


import com.sugo.smart_city.common.util.IpUtil;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "地图定位相关接口")
@RequestMapping("/commons/location")
public class LocationController {

    @Resource
    private LocationService locationService;

    @SneakyThrows
    @GetMapping("/position")
    @ApiOperation("通过用户ip获取位置信息")
    public Result position(HttpServletRequest request){
        String ipAddress = IpUtil.getIpAddr(request);
        String location = locationService.location(ipAddress);
        return Result.ok().data("location", location);
    }

    @SneakyThrows
    @GetMapping("/geocoding")
    @ApiOperation("地址信息编码为经纬度")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "address", value = "地址信息字符串如（衡阳市雁峰区黄白路金钟时代广场对面门面 （中国光大银行附近））"),
                    @ApiImplicitParam(name = "city", value = "城市名称")
            }
    )
    public Result geocoding(@RequestParam String address,
                            @RequestParam(required = false, defaultValue = "") String city){
        String geocoding = locationService.geocoding(address, city);
        return Result.ok().data("location", geocoding);
    }



}
