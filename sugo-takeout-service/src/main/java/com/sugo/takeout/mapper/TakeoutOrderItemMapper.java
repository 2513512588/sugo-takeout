package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutOrderItem;
import com.sugo.takeout.bean.vo.OrderGoodsItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sugo.takeout.model.TakeoutOrderItem
 */
public interface TakeoutOrderItemMapper extends BaseMapper<TakeoutOrderItem> {

    List<OrderGoodsItemVo> findByCode(@Param("code") String code);

}




