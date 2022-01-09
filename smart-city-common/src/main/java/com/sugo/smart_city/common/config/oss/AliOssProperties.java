package com.sugo.smart_city.common.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author hehaoyang
 */
@Data
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProperties {


      private String endPoint;
      private String keyId;
      private String keySecret;
      private String bucketName;


}
