package com.sugo.takeout.bean.event;

import com.sugo.takeout.bean.dto.SellerAdditionalDto;
import com.sugo.takeout.bean.model.TakeoutSeller;
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
public class SellerEvent {

    private List<TakeoutSeller> takeoutSeller;
    /**
     * <SellerId, AdditionDto>
     */
    private Map<Integer, SellerAdditionalDto> additionalData;
    private String currentLocation;

    public SellerEvent(List<TakeoutSeller> takeoutSeller, String currentLocation) {
        this.takeoutSeller = takeoutSeller;
        this.currentLocation = currentLocation;
        additionalData = new HashMap<>(takeoutSeller.size());
    }
}
