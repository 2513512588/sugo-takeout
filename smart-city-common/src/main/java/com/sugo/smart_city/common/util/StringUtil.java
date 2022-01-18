package com.sugo.smart_city.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sugo.smart_city.common.exception.SugoException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class StringUtil {

    public String[] formatLatLng(String location){
        try {
            String[] split = location.split(",");
            //纬度
            String lat = String.format("%.6f", Double.parseDouble(split[0]));
            //经度
            String lng = String.format("%.6f", Double.parseDouble(split[1]));
            return new String[]{lat, lng};
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
            String[] strings = formatLatLng(String.format("%s,%s", result.getFloatValue("lat"), result.getFloatValue("lng")));
            return StringUtils.join(strings, ",");
        }
        return null;
    }

}
