package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.dto.TakeoutSellerDetailDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;

/**
 * @author hehaoyang
 */
public interface TakeoutSellerService extends IService<TakeoutSeller> {

    TakeoutSellerDetailDto getDetailById(Integer id);

    TakeoutSellerDetailDto getDetailById(Integer id, String location);


}
