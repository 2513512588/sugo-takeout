package com.sugo.smart_city.api.listener;

import com.alibaba.fastjson.JSONObject;
import com.sugo.smart_city.bean.dto.TakeoutSellerAdditionalDto;
import com.sugo.smart_city.bean.event.TakeoutSellerEvent;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.service.LocationService;
import com.sugo.smart_city.service.TakeoutGoodsEvaluateService;
import com.sugo.smart_city.service.TakeoutOrderService;
import com.sugo.smart_city.service.TakeoutSellerService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TakeoutSellerListener {

    private LocationService locationService;
    private TakeoutSellerService takeoutSellerService;
    private TakeoutOrderService takeoutOrderService;
    private TakeoutGoodsEvaluateService takeoutGoodsEvaluateService;

    @EventListener(TakeoutSellerEvent.class)
    public void takeoutSellerEvent(TakeoutSellerEvent takeoutSellerEvent){
        TakeoutSeller takeoutSeller = takeoutSellerEvent.getTakeoutSeller();
        Integer sellerId = takeoutSeller.getId();
        JSONObject jsonObject = JSONObject.parseObject(takeoutSeller.getLocation());
        TakeoutSellerAdditionalDto takeoutSellerAdditionalDto = takeoutSellerEvent.getAdditionalData();

        //计算距离
        int status = jsonObject.getIntValue("status");
        if (status == 0){
            JSONObject result = jsonObject.getJSONObject("result").getJSONObject("location");
            String lng = String.format("%.6f", result.getFloatValue("lng"));
            String lat = String.format("%.6f", result.getFloatValue("lat"));
            String routematrix = locationService.routematrix(lat + "," + lng, takeoutSellerEvent.getCurrentLocation());
            JSONObject response = JSONObject.parseObject(routematrix);
            if (response.getIntValue("status") == 0){
                long distance = response.getJSONArray("result").getJSONObject(0).getJSONObject("distance").getLongValue("value");
                takeoutSellerAdditionalDto.setDistance(distance);
            }
        }
        //计算人均消费
        double avgCost = takeoutOrderService.getAvgCostBySeller(sellerId);
        takeoutSellerAdditionalDto.setAvgCost(avgCost);

        //计算评分
        Double avgScore = takeoutGoodsEvaluateService.getAvgScoreBySeller(sellerId);
        takeoutSellerAdditionalDto.setAvgScore(avgScore);

        //计算月销
        int avgMonthSoldNum = takeoutOrderService.getAvgMonthSoldNumBySeller(sellerId);
        takeoutSellerAdditionalDto.setMonthSoldNum(avgMonthSoldNum);
    }

}
