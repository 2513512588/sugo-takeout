package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sugo.takeout.bean.dto.SellerDataDto;
import com.sugo.takeout.bean.dto.SellerDetailDto;
import com.sugo.takeout.bean.model.TakeoutSeller;
import org.apache.ibatis.annotations.Param;


/**
 * @Entity com.sugo.takeout.model.TakeoutSeller
 */
public interface TakeoutSellerMapper extends BaseMapper<TakeoutSeller> {


    SellerDetailDto getDetailById(@Param("id") Integer id);

    SellerDataDto getDataById(@Param("sellerId") Integer sellerId);

}




