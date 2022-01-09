package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.common.aspect.annotation.StartPage;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.mapper.TakeoutGoodsMapper;
import com.sugo.smart_city.service.TakeoutGoodsService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TakeoutGoodsServiceImpl extends ServiceImpl<TakeoutGoodsMapper, TakeoutGoods>
    implements TakeoutGoodsService{

    @StartPage
    @Override
    public Object selectPage(IPage<TakeoutGoods> takeoutGoodsPage, TakeoutGoods takeoutGoods) {
        return baseMapper.selectPage(takeoutGoodsPage, new QueryWrapper<>(takeoutGoods));
    }
}




