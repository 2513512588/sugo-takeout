package com.sugo.smart_city.service;

import java.util.Map;

public interface MapService {

    /**
     * 获取位置的经纬度
     * @param address 位置字符串
     * @return 经纬度map
     */
    Map<String, String> getLatitude(String address);

    /**
     * 获取两地距离
     * @param startMap 起始地坐标
     * @param endMap 目标地坐标
     * @return 距离
     */
    double backDis(Map<String, String> startMap, Map<String, String> endMap);

}
