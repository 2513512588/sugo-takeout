package com.sugo.smart_city.bean.event;

import com.sugo.smart_city.bean.dto.TakeoutSellerAdditionalDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutSellerEvent {

    private TakeoutSeller takeoutSeller;
    private String currentLocation;
    private TakeoutSellerAdditionalDto additionalData;

}
