package com.sugo.takeout.service;

import java.util.List;

public interface MapService {

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

    /**
     *
     * @param origin 开始坐标
     * @param destination 目的坐标
     * @return distance
     */
    Long routematrixOne(String origin, String destination);

    /**
     *
     * @param origin 开始坐标
     * @param destination 目的坐标
     * @return distanceList
     */
    List<Long> routematrixList(String origin, String destination);

}
