package com.sugo.smart_city.common.util;

import com.alibaba.fastjson.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * java版计算signature签名
 */
public class SnCal {

    private static final String AK = "d5qwnK9O26ffCZbNU9TnTWSTH9Dydpa2";

    public static void main(String[] args) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {

        List<Integer> list = JSONArray.parseArray("dsad", Integer.class);

        SnCal snCal = new SnCal();

        // 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，
        // 该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。
        // 以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，
        // 再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。

        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("output", "json");
        paramsMap.put("address", "衡阳市雁峰区黄白路金钟时代广场对面门面 （中国光大银行附近）");
        paramsMap.put("city", "衡阳市");
        paramsMap.put("ak", AK);

        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = snCal.toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/geocoding/v3/?" + paramsStr + "5NyvSDTwWj3Spu2CFop3yHZ68q6sjX7p");

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(snCal.md5(tempStr));
    }

    /**
     * 对Map内所有value作utf8编码，拼接返回结果
     * @param data map
     * @return query 字符串
     * @throws UnsupportedEncodingException url编码异常
     */
    public String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuilder queryString = new StringBuilder();
        for (Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey()).append("=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8")).append("&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    /**
     * 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
     * @param str md5加密的字符串
     * @return md5值
     */
    public String md5(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {

        }
        return null;
    }
}