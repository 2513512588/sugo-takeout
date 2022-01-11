package com.sugo.smart_city.common.config.location;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:smart_city.properties")
@ConfigurationProperties(prefix = "baidu.map")
public class BaiduMapProperties {

    private String ak;

}
