package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutGoodsSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity TakeoutGoodsSku
 */
public interface TakeoutGoodsSkuMapper extends BaseMapper<TakeoutGoodsSku> {

    List<TakeoutGoodsSku> findByGoodsId(@Param("goodsId") Integer goodsId);
    List<TakeoutGoodsSku> findByType(@Param("goodsId") Integer goodsId, @Param("type") String type);

}




