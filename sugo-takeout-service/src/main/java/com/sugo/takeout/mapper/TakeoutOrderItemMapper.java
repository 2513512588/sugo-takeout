package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutOrderItem;
import com.sugo.takeout.bean.vo.TakeoutOrderGoodsItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.takeout.smartcity.entity.TakeoutOrderItem
 */
public interface TakeoutOrderItemMapper extends BaseMapper<TakeoutOrderItem> {

    List<TakeoutOrderGoodsItemVo> findByCode(@Param("code") String code);

}




