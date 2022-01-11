package com.sugo.smart_city.service;

public interface LocationService {

    /**
     * 通过ip获取位置信息
     */
    String location(String ip);

    /**
     * 地址信息编码为经纬度
     */
    String geocoding(String address);
    String geocoding(String address, String city);

    /**
     * 路线规划
     */
    String directionlite(String origin, String destination);

    /**
     * 计算两地距离
     */
    String routematrix(String origin, String destination);

}
