package com.sugo.takeout.rider.listener;


import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.rider.dto.TakeoutOrderDto;
import com.sugo.takeout.rider.event.RiderOrderEvent;
import com.sugo.takeout.service.MapService;
import com.sugo.takeout.service.TakeoutSellerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hehaoyang
 */
@Component
@AllArgsConstructor
@Slf4j
public class RiderOrderListener {

    private TakeoutSellerService takeoutSellerService;
    private MapService mapService;

    @EventListener(RiderOrderEvent.class)
    public void riderOrderListener(RiderOrderEvent event){
        if (event.getSource() instanceof List){
            TakeoutOrder takeoutOrder = event.getTakeoutOrder();
            TakeoutOrderDto takeoutOrderDto = new TakeoutOrderDto();
            takeoutOrderDto.setCode(takeoutOrder.getCode());

        }
    }


}
