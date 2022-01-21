package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.model.TakeoutCollection;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.mapper.TakeoutCollectionMapper;
import com.sugo.smart_city.service.TakeoutCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutCollectionServiceImpl extends ServiceImpl<TakeoutCollectionMapper, TakeoutCollection>
    implements TakeoutCollectionService{

    @Override
    public boolean exists(Integer userId, Integer sellerId, Integer goodsId) {
        QueryWrapper<TakeoutCollection> queryWrapper = new QueryWrapper<>(TakeoutCollection.builder().goodsId(goodsId).sellerId(sellerId).goodsId(goodsId).build());
        Long count = baseMapper.selectCount(queryWrapper);
        return count >= 1;
    }

    @Override
    public boolean exists(Integer userId, Integer sellerId) {
        return exists(userId, sellerId, null);
    }

    @Override
    public List<TakeoutGoods> getCollectSellerList(Integer userId) {
        return null;
    }
}




