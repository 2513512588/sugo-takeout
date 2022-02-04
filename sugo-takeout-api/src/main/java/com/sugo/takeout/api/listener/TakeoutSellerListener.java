package com.sugo.takeout.api.listener;


import com.sugo.takeout.bean.dto.SellerAdditionalDto;
import com.sugo.takeout.bean.event.SellerEvent;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutDeliveryService;
import com.sugo.takeout.service.TakeoutGoodsEvaluateService;
import com.sugo.takeout.service.TakeoutOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class TakeoutSellerListener {

    private MapService mapService;
    private TakeoutOrderService takeoutOrderService;
    private TakeoutGoodsEvaluateService takeoutGoodsEvaluateService;
    private TakeoutDeliveryService takeoutDeliveryService;


    @EventListener(SellerEvent.class)
    public void takeoutSellerEvent(SellerEvent sellerEvent){
        List<TakeoutSeller> takeoutSellerList = sellerEvent.getTakeoutSeller();

        for (TakeoutSeller item : takeoutSellerList) {
            String s = StringUtil.formatSellerLocation(item.getLocation());
            item.setLocation(s);
        }

        String join = StringUtils.join(takeoutSellerList.stream().map(TakeoutSeller::getLocation).collect(Collectors.toList()), "|");

        // sellerIds
        List<Integer> collect = takeoutSellerList.stream().map(TakeoutSeller::getId).collect(Collectors.toList());

        if (collect.size() > 0){
            log.debug("sellerListIds =>{}", collect);
            //计算人均消费
            List<Double> avgCostList = takeoutOrderService.getAvgCostBySellerList(collect);
            //计算评分
            List<Double> avgScoreList = takeoutGoodsEvaluateService.getAvgScoreBySellerList(collect);

            //计算月销
            List<Integer> avgMonthOrderNumList = takeoutOrderService.getAvgMonthOrderNumBySellerList(collect);

            //计算平均配送时间
            Map<Integer, Double> avgDeliveryTimeBySellerList = takeoutDeliveryService.getAvgDeliveryTimeBySellerList(collect);
            log.debug("avgDeliveryTimeBySellerList => {}", avgDeliveryTimeBySellerList);
            //计算距离
            Map<Integer, SellerAdditionalDto> additionalData = sellerEvent.getAdditionalData();
            List<Long> longs = mapService.routematrixList(sellerEvent.getCurrentLocation(), join);
            for (int i = 0; i < longs.size(); i++) {
                SellerAdditionalDto additionalDto = new SellerAdditionalDto();
                additionalDto.setDistance(longs.get(i));
                additionalDto.setAvgCost(avgCostList.get(i));
                additionalDto.setAvgScore(avgScoreList.get(i));
                additionalDto.setMonthOrderNum(avgMonthOrderNumList.get(i));
                additionalDto.setAvgDeliveryTime(avgDeliveryTimeBySellerList.get(collect.get(i)));
                additionalData.put(takeoutSellerList.get(i).getId(), additionalDto);
            }
        }

    }


}
