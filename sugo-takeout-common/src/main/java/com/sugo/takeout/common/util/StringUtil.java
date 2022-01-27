package com.sugo.takeout.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sugo.takeout.common.exception.SugoException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String[] formatLatLng(String location){
        return formatLatLngStr(location).split(",");
    }

    public String formatLatLngStr(String location){
        try {
            String[] split = location.split(",");
            //纬度
            String lat = String.format("%.6f", Double.parseDouble(split[0]));
            //经度
            String lng = String.format("%.6f", Double.parseDouble(split[1]));
            return String.format("%s,%s", lat, lng);
        }catch (Exception e){
            throw new SugoException("位置信息异常，坐标信息需为（纬度,经度）");
        }
    }

    /**
     * 解析商家度位置信息
     * @param json 位置json字符串
     * @return 纬度,经度
     */
    public String parseSellerLocation(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getIntValue("status");
        if (status == 0) {
            JSONObject result = jsonObject.getJSONObject("result").getJSONObject("location");
            return formatLatLngStr(result.getString("lat") + "," + result.getString("lng"));
        }
        return null;
    }

}
