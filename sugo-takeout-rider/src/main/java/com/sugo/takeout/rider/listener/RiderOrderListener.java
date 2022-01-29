package com.sugo.takeout.rider.listener;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.rider.bo.NewRiderOrderBo;
import com.sugo.takeout.rider.dto.NewRiderOrderDto;
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
        NewRiderOrderDto newRiderOrderDto = new NewRiderOrderDto();
        newRiderOrderDto.setCode(takeoutOrder.getCode());
        newRiderOrderDto.setTargetAddrName(takeoutOrder.getAddrName());
        newRiderOrderDto.setPrice(takeoutOrder.getDeliveryFee());

        QueryWrapper<TakeoutSeller> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "enterprise_address", "location", "delivery_time_start", "delivery_time_end");
        queryWrapper.eq("id", takeoutOrder.getSellerId());
        TakeoutSeller takeoutSeller = takeoutSellerService.getOne(queryWrapper);

        newRiderOrderDto.setShopName(takeoutSeller.getName());
        newRiderOrderDto.setOriginAddrDesc(takeoutSeller.getEnterpriseAddress());

        LocalDateTime now = LocalDateTime.now();

        Duration between = Duration.between(now, takeoutOrder.getArriveTime());
        newRiderOrderDto.setTimestamp(between.toMillis());


        NewRiderOrderBo newRiderOrderBo = new NewRiderOrderBo();
        newRiderOrderBo.setTakeoutOrder(newRiderOrderDto);
        newRiderOrderBo.setDeliveryTimeStart(takeoutSeller.getDeliveryTimeStart());
        newRiderOrderBo.setDeliveryTimeEnd(takeoutSeller.getDeliveryTimeEnd());
        newRiderOrderBo.setOriginLatLng(StringUtil.formatSellerLocation(takeoutSeller.getLocation()));
        newRiderOrderBo.setTargetLatLng(StringUtil.formatLatLngStr(takeoutOrder.getAddrLat() + "," + takeoutOrder.getAddrLng()));

        if (event.getSource() instanceof List){
            @SuppressWarnings("unchecked")
            List<Serializable> source = (List<Serializable>) event.getSource();
            source.add(newRiderOrderBo);
        }else if (event.getSource() instanceof Serializable){
            Object source = event.getSource();
            source = newRiderOrderBo;
        }
    }


}
