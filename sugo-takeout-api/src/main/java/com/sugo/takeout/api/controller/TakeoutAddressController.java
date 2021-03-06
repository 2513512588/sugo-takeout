package com.sugo.takeout.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.model.TakeoutAddress;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.bean.param.AddressParam;
import com.sugo.takeout.common.enums.ResultCode;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.common.valid.Groups;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutAddressService;
import com.sugo.takeout.service.TakeoutSellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hehaoyang
 */
@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = "用户收货地址接口")
@RequestMapping("/api/takeout/address")
public class TakeoutAddressController {

    private final MapperFacade mapperFacade;
    private TakeoutAddressService takeoutAddressService;
    private TakeoutSellerService takeoutSellerService;
    private MapService mapService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "myLocation", value = "当前位置信息（纬度,经度）")
    })
    @ApiOperation("获取当前用户推荐地址")
    @GetMapping("/proposal")
    public Result proposal(@ParseUser Integer userId,
                           @RequestParam String myLocation){
        QueryWrapper<TakeoutAddress> queryWrapper = new QueryWrapper<>(TakeoutAddress.builder().userId(userId).build());
        List<TakeoutAddress> list = takeoutAddressService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)){
            String location = StringUtil.formatLatLngStr(myLocation);
            List<String> collect = list.stream().map(item -> StringUtil.formatLatLngStr(item.getLat() + "," + item.getLng())).collect(Collectors.toList());
            List<Long> longs = mapService.routeMatrixDistanceList(location, StringUtils.join(collect, "|"));
            log.debug("distances => {}", longs);
            Optional<Long> first = longs.stream().sorted().findFirst();
            if (first.isPresent()){
                int index = longs.indexOf(first.get());
                return Result.ok().data(list.get(index));
            }
        }
        return Result.ok();
    }

    @ApiOperation("获取当前用户收货地址列表")
    @GetMapping("/list")
    public Result list(@ParseUser Integer userId){
        QueryWrapper<TakeoutAddress> queryWrapper = new QueryWrapper<>(TakeoutAddress.builder().userId(userId).build());
        List<TakeoutAddress> list = takeoutAddressService.list(queryWrapper);
        return Result.ok().list(list);
    }

    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "收货地址id")
    })
    @ApiOperation("当前用户根据id查询地址")
    @GetMapping("/detail/{id}")
    public Result detail(@ParseUser Integer userId, @PathVariable Integer id){
        QueryWrapper<TakeoutAddress> queryWrapper = new QueryWrapper<>(TakeoutAddress.builder().id(id).userId(userId).build());
        return Result.ok().data(takeoutAddressService.getOne(queryWrapper));
    }


    @ApiOperation("添加收货地址")
    @PostMapping("/add")
    public Result add(@ParseUser Integer userId,
                      @RequestBody @Validated(Groups.Add.class) AddressParam addressParam){
        TakeoutAddress takeoutAddress = mapperFacade.map(addressParam, TakeoutAddress.class);
        takeoutAddress.setUserId(userId);
        return Result.auto(takeoutAddressService.save(takeoutAddress));
    }



    @ApiOperation("修改收货地址")
    @PutMapping("/update")
    public Result update(@ParseUser Integer userId,
                         @RequestBody @Validated(Groups.Update.class) AddressParam addressParam){
        TakeoutAddress takeoutAddress = mapperFacade.map(addressParam, TakeoutAddress.class);
        takeoutAddress.setUserId(userId);
        return Result.auto(takeoutAddressService.updateById(takeoutAddress));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址id")
    })
    @ApiOperation("删除收货地址")
    @DeleteMapping("/del/{id}")
    public Result del(@ParseUser Integer userId,
                      @PathVariable Integer id){
        return Result.auto(takeoutAddressService.removeById(TakeoutAddress.builder().userId(userId).id(id).build()));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "卖家id"),
            @ApiImplicitParam(name = "addrId", value = "收货地址id")
    })
    @ApiOperation("根据收货地址和卖家id获取送达时间和配送费")
    @GetMapping("/deliveryTime")
    public Result deliveryTime(@ParseUser Integer userId, @RequestParam Integer sellerId, @RequestParam Integer addrId){
        QueryWrapper<TakeoutAddress> queryWrapper = new QueryWrapper<>(TakeoutAddress.builder().id(addrId).userId(userId).build());
        TakeoutAddress takeoutAddress = takeoutAddressService.getOne(queryWrapper);
        TakeoutSeller takeoutSeller = takeoutSellerService.getById(TakeoutSeller.builder().id(sellerId).build());
        if (takeoutAddress != null && takeoutSeller != null){
            String addrLocation = StringUtil.formatLatLngStr(takeoutAddress.getLat() + "," + takeoutAddress.getLng());
            String sellerLocation = StringUtil.formatSellerLocation(takeoutSeller.getLocation());
            Long distance = mapService.routeMatrixDistance(addrLocation, sellerLocation);
            return Result.ok().data("deliveryTime", takeoutAddressService.getDeliveryTime(distance))
                              .data("deliveryFee", takeoutAddressService.getDeliveryFee(distance));
        }else {
            return Result.of(ResultCode.VALIDATE_FAILED);
        }
    }

}
