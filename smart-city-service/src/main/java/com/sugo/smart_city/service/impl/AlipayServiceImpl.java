package com.sugo.smart_city.service.impl;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.sugo.smart_city.common.config.payment.AlipayConfig;
import com.sugo.smart_city.common.exception.SugoException;
import com.sugo.smart_city.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author hehaoyang
 */
@Service
public class AlipayServiceImpl implements PaymentService {

    /**
     * 订单名称
     */
    private static final String ORDER_NAME = "账户充值";

    /**
     * 和支付宝签约的产品码 固定值
     */
    private static final String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";

    /**
     * 支付成功标识(可退款的签约是TRADE_SUCCESS，不可退款的签约是TRADE_FINISHED)
     */
    private static final String TRADE_SUCCESS = "TRADE_SUCCESS";

    @Override
    public boolean notify(Map<String, String[]> parameterMap) {
        Map<String,String> map = new HashMap<String,String>();
        Set<String> keySet = parameterMap.keySet();
        for (String key : keySet) {
            String[] values = parameterMap.get(key);
            map.put(key, StringUtils.join(values, ","));
        }
        try {
            return AlipaySignature.rsaCheckV1(map, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new SugoException("异常的支付验证");
        }
    }

    @Override
    public String create(String orderNo, String total, String returnUrl, int type) {
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipayRequest<?> alipayRequest = null;
        //手机网站
        if (type == 1){
            alipayRequest = new AlipayTradeWapPayRequest();
            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            //订单编号，不可重复
            model.setOutTradeNo(orderNo);
            //订单名称
            model.setSubject(ORDER_NAME);
            //订单金额
            model.setTotalAmount(total);
            //产品吗
            model.setProductCode(PRODUCT_CODE);

            //过期时间15分钟
            model.setTimeoutExpress("15m");

            alipayRequest.setBizModel(model);
        //电脑网页
        }else if (type == 2){
            alipayRequest = new AlipayTradePagePayRequest();
            // 封装请求支付信息
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            //订单编号，不可重复
            model.setOutTradeNo(orderNo);
            //订单名称
            model.setSubject(ORDER_NAME);
            //订单金额
            model.setTotalAmount(total);
            //产品吗
            model.setProductCode(PRODUCT_CODE);

            //过期时间15分钟
            model.setTimeoutExpress("15m");

            alipayRequest.setBizModel(model);
        //手机app
        }else if (type == 3){
            alipayRequest = new AlipayTradeAppPayRequest();
            // 封装请求支付信息
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //订单编号，不可重复
            model.setOutTradeNo(orderNo);
            //订单名称
            model.setSubject(ORDER_NAME);
            //订单金额
            model.setTotalAmount(total);
            //产品吗
            model.setProductCode(PRODUCT_CODE);

            //过期时间15分钟
            model.setTimeoutExpress("15m");

            alipayRequest.setBizModel(model);
        }
        //支付成功后跳转的地址
        assert alipayRequest != null;
        alipayRequest.setReturnUrl(returnUrl);
        //异步通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        // form表单生产
        try {
            // 调用SDK生成表单 sdkExecute 生成支付链接 pageExecute生成支付的html代码
            return client.sdkExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new SugoException("支付失败！");
        }
    }


    @Override
    public void refund(String orderId) {

    }
}