package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutBasket
 */
public interface TakeoutBasketMapper extends BaseMapper<TakeoutBasket> {

    IPage<TakeoutBasketDto> selectBasketListByUser(IPage<?> page, @Param("userId") Integer userId);

    List<TakeoutBasketDto> list(@Param("userId") Integer userId, @Param("sellerId") Integer sellerId);
}




