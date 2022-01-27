package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sugo.takeout.bean.dto.TakeoutSellerDetailDto;
import com.sugo.takeout.bean.model.TakeoutSeller;


/**
 * @Entity com.takeout.smartcity.entity.TakeoutSeller
 */
public interface TakeoutSellerMapper extends BaseMapper<TakeoutSeller> {


    TakeoutSellerDetailDto getDetailById(Integer id);
}




