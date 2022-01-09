package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutGoods;

/**
 *
 */
public interface TakeoutGoodsService extends IService<TakeoutGoods> {

    Object selectPage(IPage<TakeoutGoods> takeoutGoodsPage, TakeoutGoods takeoutGoods);

}
