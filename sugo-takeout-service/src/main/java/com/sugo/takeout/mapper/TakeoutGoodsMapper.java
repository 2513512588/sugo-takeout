package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.takeout.bean.dto.TakeoutGoodsDetailDto;
import com.sugo.takeout.bean.dto.TakeoutGoodsListDto;
import com.sugo.takeout.bean.model.TakeoutGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Entity com.takeout.smartcity.entity.TakeoutGoods
 */
public interface TakeoutGoodsMapper extends BaseMapper<TakeoutGoods> {

    /**
     * todo 效率比较低 待优化
     */
    IPage<TakeoutGoodsListDto> getListByCity(IPage<TakeoutGoods> page, @Param("province") String province, @Param("city") String city, @Param("type") Integer type);

    List<TakeoutGoodsDetailDto> getListBySeller(@Param("sellerId") Integer sellerId, @Param("goodsCategoryId") Integer goodsCategoryId);

    TakeoutGoods findById(Integer id);
}




