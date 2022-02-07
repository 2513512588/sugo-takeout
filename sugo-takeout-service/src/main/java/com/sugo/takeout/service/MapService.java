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
     * @param origin 开始坐标
     * @param destination 目的坐标
     * @return 距离（米/公里） 时间（分钟/小时）
     */
    List<long []> routeMatrix(String origin, String destination);
    long [] routeMatrixOne(String origin, String destination);

    /**
     * 获取两地的距离
     * @param origin 开始坐标
     * @param destination 目的坐标
     * @return distance 距离（米）
     */
    Long routeMatrixDistance(String origin, String destination);

    /**
     * 批量获取两地的距离
     * @param origin 开始坐标
     * @param destination 目的坐标
     * @return distanceList
     */
    List<Long> routeMatrixDistanceList(String origin, String destination);

}
