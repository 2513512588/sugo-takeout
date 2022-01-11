package com.sugo.smart_city.service.impl;

import com.sugo.smart_city.common.config.location.BaiduMapProperties;
import com.sugo.smart_city.common.util.IpUtil;
import com.sugo.smart_city.service.LocationService;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;

@Service
public class LocationServiceImpl implements LocationService {

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
        HttpGet get = new HttpGet(String.format("http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=%s&destinations=%s&ak=%s", origin, destination, baiduMapProperties.getAk()));
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }
}
