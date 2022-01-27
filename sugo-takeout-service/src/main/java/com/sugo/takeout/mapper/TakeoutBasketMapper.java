package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.takeout.bean.dto.TakeoutBasketDto;
import com.sugo.takeout.bean.model.TakeoutBasket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.takeout.smartcity.entity.TakeoutBasket
 */
public interface TakeoutBasketMapper extends BaseMapper<TakeoutBasket> {

    IPage<TakeoutBasketDto> selectBasketListByUser(IPage<?> page, @Param("userId") Integer userId);

    List<TakeoutBasketDto> list(@Param("userId") Integer userId, @Param("sellerId") Integer sellerId, @Param("status") Integer goodsStatus);
}




