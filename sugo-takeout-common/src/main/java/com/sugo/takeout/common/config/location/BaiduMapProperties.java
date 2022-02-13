package com.sugo.takeout.common.config.location;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:sugo_takeout.properties")
@ConfigurationProperties(prefix = "baidu.map")
public class BaiduMapProperties {

    private String ak;

}
