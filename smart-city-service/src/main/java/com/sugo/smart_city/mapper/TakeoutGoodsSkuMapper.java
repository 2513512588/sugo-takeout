package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutGoodsSku;

import java.util.List;

/**
 * @Entity com.sugo.smart_city.bean.model.TakeoutGoodsSku
 */
public interface TakeoutGoodsSkuMapper extends BaseMapper<TakeoutGoodsSku> {

    List<TakeoutGoodsSku> findByGoodsId(Integer goodsId);

}




