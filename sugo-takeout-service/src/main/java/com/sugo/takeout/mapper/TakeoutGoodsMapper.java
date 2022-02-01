package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.GoodsDetailDto;
import com.sugo.takeout.bean.dto.GoodsListDto;
import com.sugo.takeout.bean.model.TakeoutGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Entity com.sugo.takeout.model.TakeoutGoods
 */
public interface TakeoutGoodsMapper extends BaseMapper<TakeoutGoods> {

    /**
     * todo 效率比较低 待优化
     */
    IPage<GoodsListDto> getListByCity(Page<TakeoutGoods> page, @Param("province") String province, @Param("city") String city, @Param("type") Integer type);

    List<GoodsDetailDto> getListBySeller(@Param("sellerId") Integer sellerId, @Param("goodsCategoryId") Integer goodsCategoryId);

    TakeoutGoods findById(Integer id);
}




