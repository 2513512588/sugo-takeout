package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutActivity;

import java.util.List;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutActivity
 */
public interface TakeoutActivityMapper extends BaseMapper<TakeoutActivity> {

    List<TakeoutActivity> findBySellerId(Integer id);

}




