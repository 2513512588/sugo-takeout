package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutGoodsDto;
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


    @Override
    public IPage<TakeoutGoodsDto> getListByCity(String province, String city, Integer type, IPage<TakeoutGoods> page) {
        return baseMapper.getListByCity(page, province, city, type);
    }
}




