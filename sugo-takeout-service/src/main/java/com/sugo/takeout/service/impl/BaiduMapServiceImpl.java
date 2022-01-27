package com.sugo.takeout.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sugo.takeout.common.config.location.BaiduMapProperties;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.service.MapService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BaiduMapServiceImpl implements MapService {

    private final HttpClient client = HttpClientBuilder.create().build();

    @Resource
    private BaiduMapProperties baiduMapProperties;

    @SneakyThrows
    @Override
    public String location(String ip) {
        HttpGet get = new HttpGet(String.format("http://api.map.baidu.com/location/ip?ak=%s&ip=%s&coor=bd09ll", baiduMapProperties.getAk(), ip));
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    @SneakyThrows
    @Override
    public String geocoding(String address) {
        return geocoding(address, "");
    }

    @SneakyThrows
    @Override
    public String geocoding(String address, String city) {
        HttpGet get = new HttpGet(String.format("http://api.map.baidu.com/geocoding/v3/?address=%s&city=%s&output=json&ak=%s", URLEncoder.encode(address, "UTF-8"), URLEncoder.encode(city, "UTF-8"), baiduMapProperties.getAk()));
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    @SneakyThrows
    @Override
    public String directionlite(String origin, String destination) {
        HttpGet get = new HttpGet(String.format("https://api.map.baidu.com/directionlite/v1/driving?origin=%s&destination=%s&ak=%s", origin, destination, baiduMapProperties.getAk()));
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }


    @SneakyThrows
    @Override
    public String routematrix(String origin, String destination) {
        HttpGet get = new HttpGet(String.format("http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=%s&destinations=%s&ak=%s", URLEncoder.encode(origin, "UTF-8"), URLEncoder.encode(destination, "UTF-8"), baiduMapProperties.getAk()));
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public Long routematrixOne(String origin, String destination){
        log.debug("destination => {}", destination);
        log.debug("origin => {}", origin);
        JSONObject response = JSONObject.parseObject(routematrix(origin, destination));
        if (response.getIntValue("status") == 0){
            return response.getJSONArray("result").getJSONObject(0).getJSONObject("distance").getLongValue("value");
        }else {
            throw new SugoException(response.getString("message") + "，经纬度异常，格式为（纬度,经度）");
        }
    }

    @Override
    public List<Long> routematrixList(String origin, String destination){
        log.debug("destination => {}", destination);
        log.debug("origin => {}", origin);
        JSONObject response = JSONObject.parseObject(routematrix(origin, destination));
        List<Long> resultList = new ArrayList<>();
        if (response.getIntValue("status") == 0){
            JSONArray result = response.getJSONArray("result");
            for (int i = 0; i < result.size(); i++) {
                long distance = result.getJSONObject(i).getJSONObject("distance").getLongValue("value");
                resultList.add(distance);
            }
        }else {
            throw new SugoException(response.getString("message") + ": 经纬度异常，格式为（纬度,经度）");
        }
        return resultList;
    }
}
