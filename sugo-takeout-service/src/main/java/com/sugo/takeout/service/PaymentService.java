package com.sugo.takeout.service;






import java.util.Map;


public interface PaymentService {

    String create(String orderId, String total, String returnUrl, int type);

    boolean notify(Map<String, String[]> parameterMap);

    void refund(String orderId);

}
