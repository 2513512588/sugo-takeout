package com.sugo.takeout.rider.bo;

import com.sugo.takeout.rider.dto.NewRiderOrderDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author hehaoyang
 */
@Data
public class NewRiderOrderBo implements Serializable {


    /**
     * 配送订单对象
     */
    private NewRiderOrderDto takeoutOrder;

    /**
     * 店铺开业时间
     */
    private LocalTime deliveryTimeStart;

    /**
     * 店铺打烊时间
     */
    private LocalTime deliveryTimeEnd;

    /**
     * 起点经纬度
     */
    private String originLatLng;

    /**
     * 目标地址经纬度
     */
    private String targetLatLng;

    /**
     * 判断是否在配送时间段
     */
    public boolean isEnable(){
        LocalTime now = LocalTime.now();
        return now.isAfter(deliveryTimeStart) && now.isBefore(deliveryTimeEnd);
    }

}
