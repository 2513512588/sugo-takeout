package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutGoodsDetailDto;
import com.sugo.smart_city.bean.dto.TakeoutGoodsListDto;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.mapper.TakeoutGoodsMapper;
import com.sugo.smart_city.service.TakeoutGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 */
@Service
public class TakeoutGoodsServiceImpl extends ServiceImpl<TakeoutGoodsMapper, TakeoutGoods>
    implements TakeoutGoodsService{


    @Override
    public IPage<TakeoutGoodsListDto> getListByCity(String province, String city, Integer type, IPage<TakeoutGoods> page) {
        return baseMapper.getListByCity(page, province, city, type);
    }

    @Override
    public List<TakeoutGoodsDetailDto> getListBySeller(Integer sellerId, Integer goodsCategoryId) {
        return baseMapper.getListBySeller(sellerId, goodsCategoryId);
    }

    @Override
    public List<TakeoutGoodsDetailDto> getListBySeller(Integer sellerId) {
        return baseMapper.getListBySeller(sellerId, null);
    }
}




