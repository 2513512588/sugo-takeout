package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutGoodsEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sugo.takeout.model.TakeoutGoodsEvaluate
 */
public interface TakeoutGoodsEvaluateMapper extends BaseMapper<TakeoutGoodsEvaluate> {

    Double getAvgScoreBySeller(@Param("sellerId") Integer sellerId);

    List<Double> getAvgScoreBySellerList(@Param("sellerIds") List<Integer> collect);
}




