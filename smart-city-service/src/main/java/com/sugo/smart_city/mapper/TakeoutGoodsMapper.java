package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutGoodsDetailDto;
import com.sugo.smart_city.bean.dto.TakeoutGoodsListDto;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Entity com.smart_city.smartcity.entity.TakeoutGoods
 */
public interface TakeoutGoodsMapper extends BaseMapper<TakeoutGoods> {

    /**
     * todo 效率比较低 待优化
     */
    IPage<TakeoutGoodsListDto> getListByCity(IPage<TakeoutGoods> page, @Param("province") String province, @Param("city") String city, @Param("type") Integer type);

    List<TakeoutGoodsDetailDto> getListBySeller(@Param("sellerId") Integer sellerId, @Param("goodsCategoryId") Integer goodsCategoryId);

    TakeoutGoods findById(Integer id);
}




