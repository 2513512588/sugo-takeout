package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;

/**
 *
 */
public interface TakeoutBasketService extends IService<TakeoutBasket> {

    IPage<TakeoutBasketDto> selectPage(Page<TakeoutBasketDto> basketPage, Integer userId);
}
