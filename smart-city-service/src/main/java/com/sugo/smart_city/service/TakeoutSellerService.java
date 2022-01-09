package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.dto.TakeoutSellerDto;

/**
 *
 */
public interface TakeoutSellerService extends IService<TakeoutSeller> {

    Object selectPage(IPage<TakeoutSellerDto> takeoutSellerPage, TakeoutSeller takeoutSeller);
}
