package com.sugo.takeout.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sugo.takeout.common.exception.SugoException;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

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
            if (!Pattern.matches("^[\\-+]?((0|([1-8]\\d?))(\\.\\d{1,10})?|90(\\.0{1,10})?)$", lat)){
                throw new SugoException("纬度格式不正确");
            }
            //经度
            String lng = String.format("%.6f", Double.parseDouble(split[1]));
            if (!Pattern.matches("^[\\-+]?(0(\\.\\d{1,10})?|([1-9](\\d)?)(\\.\\d{1,10})?|1[0-7]\\d{1}(\\.\\d{1,10})?|180\\.0{1,10})$", lng)){
                throw new SugoException("经度格式不正确");
            }
            return String.format("%s,%s", lat, lng);
        }catch (Exception e){
            throw new SugoException("位置信息异常，坐标信息需为（纬度,经度）");
        }
    }


    /**
     * 格式化商家度位置信息
     * @param json 位置json字符串
     * @return 纬度,经度
     */
    public String formatSellerLocation(String json){
        return formatLatLngStr(parseSellerLocation(json));
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
            return result.getString("lat") + "," + result.getString("lng");
        }
        return null;
    }

}
