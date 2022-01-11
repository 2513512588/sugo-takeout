package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutGoodsEvaluate;

/**
 *
 */
public interface TakeoutGoodsEvaluateService extends IService<TakeoutGoodsEvaluate> {

    Double getAvgScoreBySeller(Integer sellerId);
}
