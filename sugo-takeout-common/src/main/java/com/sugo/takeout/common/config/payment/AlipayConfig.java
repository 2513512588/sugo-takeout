package com.sugo.takeout.common.config.payment;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author hehaoyang
 */
@Data
@Component
@PropertySource("classpath:sugo_takeout.properties")
@ConfigurationProperties(prefix = "ali.pay")
public class AlipayConfig {

    public String baseUrl;

    /**
     * 沙箱appid
     */
    public String appId;

    /**
     * 应用私钥 私钥 pkcs8格式的
     */
    public String rsaPrivateKey;

    /**
     * 请求网关  固定
     */
    public String gatewayUrl;

    /**
     * 异步通知地址
     */
    public String notifyUrl;

    /**
     * 编码
     */
    public String charset;

    /**
     * 返回格式
     */
    public String format;

    /**
     * 支付宝公钥
     */
    public String alipayPublicKey;
    /**
     * RSA2
     */
    public String signType;


}
