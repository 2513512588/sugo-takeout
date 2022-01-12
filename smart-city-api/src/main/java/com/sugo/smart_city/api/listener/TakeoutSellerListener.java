package com.sugo.smart_city.api.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sugo.smart_city.bean.dto.TakeoutSellerAdditionalDto;
import com.sugo.smart_city.bean.event.TakeoutSellerEvent;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.service.LocationService;
import com.sugo.smart_city.service.TakeoutGoodsEvaluateService;
import com.sugo.smart_city.service.TakeoutOrderService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TakeoutSellerListener {

    private LocationService locationService;
    private TakeoutOrderService takeoutOrderService;
    private TakeoutGoodsEvaluateService takeoutGoodsEvaluateService;

    @EventListener(TakeoutSellerEvent.class)
    public void takeoutSellerEvent(TakeoutSellerEvent takeoutSellerEvent){
        List<TakeoutSeller> takeoutSellerList = takeoutSellerEvent.getTakeoutSeller();

        for (TakeoutSeller item : takeoutSellerList) {
            JSONObject jsonObject = JSONObject.parseObject(item.getLocation());
            int status = jsonObject.getIntValue("status");
            if (status == 0) {
                JSONObject result = jsonObject.getJSONObject("result").getJSONObject("location");
                String lng = String.format("%.6f", result.getFloatValue("lng"));
                String lat = String.format("%.6f", result.getFloatValue("lat"));
                String location = lat + "," + lng;
                item.setLocation(location);
            }
        }

        String join = StringUtils.join(takeoutSellerList.stream().map(TakeoutSeller::getLocation).collect(Collectors.toList()), "|");

        List<Integer> collect = takeoutSellerList.stream().map(TakeoutSeller::getId).collect(Collectors.toList());
        //计算人均消费
        List<Double> avgCostList = takeoutOrderService.getAvgCostBySellerList(collect);
        //计算评分
        List<Double> avgScoreList = takeoutGoodsEvaluateService.getAvgScoreBySellerList(collect);

        //计算月销
        List<Integer> avgMonthSoldNumList = takeoutOrderService.getAvgMonthSoldNumBySellerList(collect);

        //计算距离
        String routematrix = locationService.routematrix(takeoutSellerEvent.getCurrentLocation(), join);
        JSONObject response = JSONObject.parseObject(routematrix);
        Map<Integer, TakeoutSellerAdditionalDto> additionalData = takeoutSellerEvent.getAdditionalData();
        if (response.getIntValue("status") == 0){
            JSONArray result = response.getJSONArray("result");
            for (int i = 0; i < result.size(); i++) {
                long distance = result.getJSONObject(i).getJSONObject("distance").getLongValue("value");
                TakeoutSellerAdditionalDto additionalDto = new TakeoutSellerAdditionalDto();
                additionalDto.setDistance(distance);
                additionalDto.setAvgCost(avgCostList.get(i));
                additionalDto.setAvgScore(avgScoreList.get(i));
                additionalDto.setMonthSoldNum(avgMonthSoldNumList.get(i));
                additionalData.put(takeoutSellerList.get(i).getId(), additionalDto);
            }
        }else {
            throw new SysException(response.getString("message") + "，经纬度异常，格式为（纬度,经度）");
        }

    }


}
