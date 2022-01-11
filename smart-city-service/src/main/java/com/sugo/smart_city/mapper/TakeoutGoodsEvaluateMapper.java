package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutGoodsEvaluate;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutGoodsEvaluate
 */
public interface TakeoutGoodsEvaluateMapper extends BaseMapper<TakeoutGoodsEvaluate> {

    Double getAvgScoreBySeller(@Param("sellerId") Integer sellerId);
}




