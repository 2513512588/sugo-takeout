package com.sugo.smart_city.bean.event;

import com.sugo.smart_city.bean.dto.TakeoutSellerAdditionalDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hehaoyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutSellerEvent {

    private List<TakeoutSeller> takeoutSeller;
    private Map<Integer, TakeoutSellerAdditionalDto> additionalData;
    private String currentLocation;

    public TakeoutSellerEvent(List<TakeoutSeller> takeoutSeller, String currentLocation) {
        this.takeoutSeller = takeoutSeller;
        this.currentLocation = currentLocation;
        additionalData = new HashMap<>(takeoutSeller.size());
    }
}
