package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutCollection;
import com.sugo.smart_city.bean.model.TakeoutGoods;

import java.util.List;

/**
 *
 */
public interface TakeoutCollectionService extends IService<TakeoutCollection> {

    boolean exists(Integer userId, Integer sellerId, Integer goodsId);

    List<TakeoutGoods> getCollectSellerList(Integer userId);
}
