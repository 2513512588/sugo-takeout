package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.takeout.bean.dto.BasketDto;
import com.sugo.takeout.bean.model.TakeoutBasket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sugo.takeout.model.TakeoutBasket
 */
public interface TakeoutBasketMapper extends BaseMapper<TakeoutBasket> {

    IPage<BasketDto> selectBasketListByUser(IPage<?> page, @Param("userId") Integer userId);

    List<BasketDto> list(@Param("userId") Integer userId, @Param("sellerId") Integer sellerId, @Param("status") Integer goodsStatus);
}




