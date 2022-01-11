package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutGoodsEvaluate;

import java.util.List;

/**
 *
 */
public interface TakeoutGoodsEvaluateService extends IService<TakeoutGoodsEvaluate> {

    Double getAvgScoreBySeller(Integer sellerId);

    List<Double> getAvgScoreBySellerList(List<Integer> collect);
}
