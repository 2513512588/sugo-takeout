package com.sugo.takeout.common.util;

import com.sugo.takeout.common.config.payment.AlipayConfig;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AntUtil {

    private static String HOST;

    public static String getHost(HttpServletRequest request){
        if (StringUtils.isEmpty(HOST)){
            if (StringUtils.isEmpty(AlipayConfig.BASE_URL)){
                return HOST = "http://" + request.getServerName() + ":" + request.getServerPort();
            }else {
                return HOST = AlipayConfig.BASE_URL;
            }
        }else {
            return HOST;
        }
    }

}
