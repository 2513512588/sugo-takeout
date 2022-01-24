package com.sugo.smart_city.common.config.payment;


import org.springframework.context.annotation.Configuration;


/**
 * @author hehaoyang
 */
@Configuration
public class AlipayConfig {

    public static String BASE_URL = "http://vdutbq.natappfree.cc";

    /**
     * 沙箱appid
     */
    public static final String APPID = "2021000116660041";

    /**
     * 应用私钥 私钥 pkcs8格式的
     */
    public static final String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCl/6didjAM+V9gDidoiKSbvM1VQwpBJK2GiAFstBeL4NGaH3zhdPsgqvXfmvY+YZUPpSc6yi2eCS4JTuo3R5cHO7r/ZRIgNLIrvRQ/IIa2+LBThBUIhgpqhef3PdeYyq4GSDqiMimvrJTAgzeSw3irYuXjkC2MW2Uf0RjpZQlXzFeE6HTawD6jq4OiXMwCZQ/TtgJ38m/jTDK0+pkaajqOwnR6WZXuMAtnkt5REn1D79KyDkq5nQrWmU5g3bKHkJw+TbsJrzn/E4B+9/AP8B4AXQNWhNmqA5xV92CB4j6+AtQlMER1kCr1c96oyLvkcC+wICofzaQQdQf91gU6L6DNAgMBAAECggEAXBYKwTBXT5s3MY4ohnDnAA4oyle6ZJD4Ql1IjQkK1uoM3LcDC/9dIK42X6kTWV/A1UEFFjP6QmVNSJSikbABNF4KSovDFmKlRGZLyQk+MlqbVnk7QdM6DAXkvN9trrEhARMok7nILlBK5NDmD2ConErsxOTCn85DtvbEHxWRrUYjmDkejRG+KhTmTPNHqHocBNSIyL5vMTxKMRslnCuwOPNLDwryzzwXRJxPcPoONRKFcKru75JAxEE9uawqSgT7/Sd6ax3qpjxclEL6/C2tccY5oAVvcdNGcZHMZR5eeOysex7GEe4zW31Qv2Ic9zTvc8oA2dUPlv84pWO8yB4F8QKBgQDsb/VaqxV6KzKdRxlc/ZnKjsZuk9K/tNCqfkIHF7JQMvAIbwFOmqY9DhoQ1A5TRhWVy2NEakTsnhxsXywqW9K2GBZkKFb40ClRvo1jfOeHFEaIJxQ60ICoMqj/0i6551ystXw1wEx2xTZx9wvfD5tRYcCK4gYZKs//w9WqdDeFNwKBgQCzu7cIdFVfsbZFSm/ueKDDxRY89kvaUfJz3j4OwyOI3zgoQrupwDXMNDYa/evOZvZqDoUsJbTk6dzUhdWrR0PXi1XnA9+xK3xJnhXGZ6dDzvdyU00/dPfeVOBgPkkq0z851IOwLaTIXxpw26DkM5iv8KCKOw/kCrDy5pI9HZoMGwKBgE17fzdgW6B2qZabJzevMAgOc/7m2KjUc5F74N18KYZYuwq4fgApdBMMHQSoya0GiapWRci03rOAOSZWph2Xi9z61dOq2J2fcfsE7EbPNvywa1DRJbMEgzUbrQBw+7kWletPak4fH6/If0gnwq8t6SNpWnTpD0COJC3YGpxKi6tPAoGAXd/vq5THeqHhZ7OHXvDWVyo7gT/LQo6GqgosM52sk7KzLCQezHy+HJgwokx5u2Z/0ckfuX1ATEfxtXbpsPoZ7xyxk2Wskmb6FaFiEizVak9RYv8rW7eGNzPxtZMSuDF51oSu+3f7l0RFFSlBka95UnvF/iz3Ec6buWLAgvdRHH0CgYA1ydorhZJjEDFhA3NN6wskNSk4sO8/HcyboPY8hgWuMX8wDERXKjhL9pnR/qOWQwiJbdem8tVCXL5MP8udvY7W/a8G62t9uno9MlRXIS7tb3i1ERjBZNYLu44v15zTJbxg9Md+qBuZMpmd4bFyhzPkdt5pUel7rr8+ioGmMG0c6g==";

    /**
     * 请求网关  固定
     */
    public static final String URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 异步通知地址
     */
    public static final String NOTIFY_URL = BASE_URL + "/api/takeout/order/pay/notify";

    /**
     * 编码
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 返回格式
     */
    public static final String FORMAT = "json";

    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk67ZZ15oRD/CYrNySXVc+GDvsDKetj6k38UU1Sy3mcZYpgG8VCqsQ6vw3plzXVS4l1I3wAgFUOuRz/g67CdDmXHvdY4RgeVsMNTDi+aI/m7PyFvNa6FEqWGBT2eGj8HMh8i3BR+962UNCnMVvsHGlfyJKb4RJWzamqTtrKi6q1SvOT+RPrTnBACtGKxjn977v5dp8VTdcZ+6l8sYKAEkeKKPYnpQrUqXxrtgDuxs8cV04Xm5lDb3Pv3uNwvyYlPKXntO8vWNZDcjx9SvUvVNI7XXyWYbbLObeMVtv4G37njKx+n6+QvMLyLTiFEi0O5JkH1wwGQ7v9ehnNKYwdLLAQIDAQAB";

    /**
     * RSA2
     */
    public static final String SIGN_TYPE = "RSA2";


}
