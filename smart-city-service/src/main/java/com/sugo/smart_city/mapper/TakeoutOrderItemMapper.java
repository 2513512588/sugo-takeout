package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutOrderItem;
import com.sugo.smart_city.bean.vo.TakeoutOrderGoodsItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutOrderItem
 */
public interface TakeoutOrderItemMapper extends BaseMapper<TakeoutOrderItem> {

    List<TakeoutOrderGoodsItemVo> findByCode(@Param("code") String code);

}




