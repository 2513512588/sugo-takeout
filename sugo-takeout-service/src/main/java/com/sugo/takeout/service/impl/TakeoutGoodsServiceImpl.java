package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.dto.GoodsDetailDto;
import com.sugo.takeout.bean.dto.GoodsListDto;
import com.sugo.takeout.bean.model.TakeoutGoods;
import com.sugo.takeout.mapper.TakeoutGoodsMapper;
import com.sugo.takeout.service.TakeoutGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 */
@Service
public class TakeoutGoodsServiceImpl extends ServiceImpl<TakeoutGoodsMapper, TakeoutGoods>
    implements TakeoutGoodsService{


    @Override
    public IPage<GoodsListDto> getListByCity(String province, String city, Integer type, Page<TakeoutGoods> page) {
        return baseMapper.getListByCity(page, province, city, type);
    }

    @Override
    public List<GoodsDetailDto> getListBySeller(Integer sellerId, Integer goodsCategoryId) {
        return baseMapper.getListBySeller(sellerId, goodsCategoryId);
    }

    @Override
    public List<GoodsDetailDto> getListBySeller(Integer sellerId) {
        return baseMapper.getListBySeller(sellerId, null);
    }
}




