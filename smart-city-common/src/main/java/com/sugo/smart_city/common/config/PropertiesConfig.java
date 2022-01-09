package com.sugo.smart_city.common.config;

import com.sugo.smart_city.common.config.oss.AliOssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author HHY
 */
@Configuration
@EnableConfigurationProperties({AliOssProperties.class})
public class PropertiesConfig {}
