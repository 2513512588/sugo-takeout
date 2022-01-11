package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.model.TakeoutGoodsEvaluate;
import com.sugo.smart_city.mapper.TakeoutGoodsEvaluateMapper;
import com.sugo.smart_city.service.TakeoutGoodsEvaluateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutGoodsEvaluateServiceImpl extends ServiceImpl<TakeoutGoodsEvaluateMapper, TakeoutGoodsEvaluate>
    implements TakeoutGoodsEvaluateService{

    @Override
    public Double getAvgScoreBySeller(Integer sellerId) {
        return baseMapper.getAvgScoreBySeller(sellerId);
    }

    @Override
    public List<Double> getAvgScoreBySellerList(List<Integer> collect) {
        return baseMapper.getAvgScoreBySellerList(collect);
    }
}




