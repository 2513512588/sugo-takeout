package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutCollection;
import com.sugo.smart_city.bean.model.TakeoutGoods;

import java.util.List;

/**
 *
 */
public interface TakeoutCollectionService extends IService<TakeoutCollection> {

    /**
     * @param userId 用户id
     * @param sellerId 卖家id
     * @param goodsId 商品id
     * @return 是否收藏
     */
    boolean exists(Integer userId, Integer sellerId, Integer goodsId);

    boolean exists(Integer userId, Integer sellerId);

    List<TakeoutGoods> getCollectSellerList(Integer userId);
}
