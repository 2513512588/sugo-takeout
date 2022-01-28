package com.sugo.takeout.rider.listener;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.rider.dto.TakeoutOrderDto;
import com.sugo.takeout.rider.event.RiderOrderEvent;
import com.sugo.takeout.service.TakeoutSellerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hehaoyang
 */
@Component
@AllArgsConstructor
@Slf4j
public class RiderOrderListener {

    private TakeoutSellerService takeoutSellerService;

    @EventListener(RiderOrderEvent.class)
    public void riderOrderListener(RiderOrderEvent event){
        TakeoutOrder takeoutOrder = event.getTakeoutOrder();
        TakeoutOrderDto takeoutOrderDto = new TakeoutOrderDto();
        takeoutOrderDto.setCode(takeoutOrder.getCode());
        takeoutOrderDto.setTargetAddrName(takeoutOrder.getAddrName());
        takeoutOrderDto.setPrice(takeoutOrder.getDeliveryFee());

        QueryWrapper<TakeoutSeller> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "enterprise_address", "location");
        queryWrapper.eq("id", takeoutOrder.getSellerId());
        TakeoutSeller takeoutSeller = takeoutSellerService.getOne(queryWrapper);

        takeoutOrderDto.setShopName(takeoutSeller.getName());
        takeoutOrderDto.setOriginAddrDesc(takeoutSeller.getEnterpriseAddress());

        LocalDateTime now = LocalDateTime.now();

        Duration between = Duration.between(now, takeoutOrder.getArriveTime());
        takeoutOrderDto.setTimestamp(between.toMillis());
        takeoutOrderDto.setOriginLatLng(StringUtil.parseSellerLocation(takeoutSeller.getLocation()));
        takeoutOrderDto.setTargetLatLng(StringUtil.formatLatLngStr(takeoutOrder.getAddrLat() + "," + takeoutOrder.getAddrLng()));
        if (event.getSource() instanceof List){
            @SuppressWarnings("unchecked")
            List<Serializable> source = (List<Serializable>) event.getSource();
            source.add(takeoutOrderDto);
        }else if (event.getSource() instanceof Serializable){
            Object source = event.getSource();
            source = takeoutOrderDto;
        }
    }


}
