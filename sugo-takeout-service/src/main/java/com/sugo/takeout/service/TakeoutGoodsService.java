package com.sugo.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.sugo.takeout.bean.dto.TakeoutGoodsDetailDto;
import com.sugo.takeout.bean.dto.TakeoutGoodsListDto;
import com.sugo.takeout.bean.model.TakeoutGoods;

import java.util.List;


/**
 *
 */
public interface TakeoutGoodsService extends IService<TakeoutGoods> {

    /**
     * 获取对应城市的推荐商品
     */
    IPage<TakeoutGoodsListDto> getListByCity(String province, String city, Integer type, Page<TakeoutGoods> page);

    List<TakeoutGoodsDetailDto> getListBySeller(Integer sellerId, Integer goodsCategoryId);
    List<TakeoutGoodsDetailDto> getListBySeller(Integer sellerId);
}
