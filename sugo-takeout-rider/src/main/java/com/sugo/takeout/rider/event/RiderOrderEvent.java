package com.sugo.takeout.rider.event;

import com.sugo.takeout.bean.model.TakeoutOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RiderOrderEvent extends ApplicationEvent {

    private final TakeoutOrder takeoutOrder;

    public RiderOrderEvent(Object source, TakeoutOrder takeoutOrder) {
        super(source);
        this.takeoutOrder = takeoutOrder;
    }
}
