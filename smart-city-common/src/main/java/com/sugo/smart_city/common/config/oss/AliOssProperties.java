package com.sugo.smart_city.common.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author hehaoyang
 */
@Data
@Component
@PropertySource("classpath:smart_city.properties")
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProperties {


      private String endPoint;
      private String keyId;
      private String keySecret;
      private String bucketName;


}
