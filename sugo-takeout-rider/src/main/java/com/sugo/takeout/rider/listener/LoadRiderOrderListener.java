package com.sugo.takeout.rider.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.takeout.bean.enums.OrderStatus;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.rider.bo.NewRiderOrderBo;
import com.sugo.takeout.rider.event.RiderOrderEvent;
import com.sugo.takeout.service.TakeoutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目启动时加载需要配送的订单
 */
@Component
public class LoadRiderOrderListener implements ApplicationRunner {

    @Autowired
    private TakeoutOrderService takeoutOrderService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 加载需要配送的订单
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        TakeoutOrder takeoutOrder = new TakeoutOrder();
        takeoutOrder.setStatus(OrderStatus.PAID.getStatus());
        QueryWrapper<TakeoutOrder> queryWrapper = new QueryWrapper<>(takeoutOrder);
        queryWrapper.select("code", "seller_id", "addr_name", "addr_detail", "addr_house_number", "addr_lat", "addr_lng", "arrive_time", "delivery_fee");
        queryWrapper.isNull("rider_id");
        List<TakeoutOrder> list = takeoutOrderService.list(queryWrapper);
        List<NewRiderOrderBo> orderBos = new ArrayList<>();
        for (TakeoutOrder order : list) {
            applicationContext.publishEvent(new RiderOrderEvent(orderBos, order));
        }
        Map<String, NewRiderOrderBo> collect = orderBos.stream().collect(Collectors.toMap(item -> item.getTakeoutOrder().getCode(), item -> item));
        RedisUtil.putH("riderOrder", collect);
    }
}
