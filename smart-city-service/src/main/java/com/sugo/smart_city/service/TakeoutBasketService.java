package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import com.sugo.smart_city.bean.param.TakeoutBasketParam;

/**
 *
 */
public interface TakeoutBasketService extends IService<TakeoutBasket> {

    IPage<TakeoutBasketDto> selectPage(Page<TakeoutBasketDto> basketPage, Integer userId);

    /**
     * 修改购物车加购数量
     */
    boolean updateQuantity(Integer userId, TakeoutBasketParam takeoutBasketParam);

}
