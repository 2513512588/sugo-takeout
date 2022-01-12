package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sugo.smart_city.bean.dto.TakeoutSellerDetailDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;


/**
 * @Entity com.smart_city.smartcity.entity.TakeoutSeller
 */
public interface TakeoutSellerMapper extends BaseMapper<TakeoutSeller> {


    TakeoutSellerDetailDto getDetailById(Integer id);
}




