package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.model.TakeoutGoodsEvaluate;

import java.util.List;

/**
 *
 */
public interface TakeoutGoodsEvaluateService extends IService<TakeoutGoodsEvaluate> {

    Double getAvgScoreBySeller(Integer sellerId);

    List<Double> getAvgScoreBySellerList(List<Integer> collect);
}
