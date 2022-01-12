package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.sugo.smart_city.bean.dto.TakeoutGoodsDto;
import com.sugo.smart_city.bean.model.TakeoutGoods;



/**
 *
 */
public interface TakeoutGoodsService extends IService<TakeoutGoods> {

    /**
     * 获取对应城市的推荐商品
     */
    IPage<TakeoutGoodsDto> getListByCity(String province, String city, Integer type, IPage<TakeoutGoods> page);
}
