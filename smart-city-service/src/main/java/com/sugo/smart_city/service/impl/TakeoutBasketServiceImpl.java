package com.sugo.smart_city.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import com.sugo.smart_city.common.aspect.annotation.StartPage;
import com.sugo.smart_city.mapper.TakeoutBasketMapper;
import com.sugo.smart_city.service.TakeoutBasketService;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
public class TakeoutBasketServiceImpl extends ServiceImpl<TakeoutBasketMapper, TakeoutBasket>
    implements TakeoutBasketService {


    @StartPage
    @Override
    public Object selectPage(Page<TakeoutBasketDto> basketPage, Integer userId) {
        return baseMapper.selectBasketListByUser(basketPage, userId);
    }
}




