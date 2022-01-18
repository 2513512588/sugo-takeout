package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutActivity;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutActivity
 */
public interface TakeoutActivityMapper extends BaseMapper<TakeoutActivity> {

    TakeoutActivity findBySellerId(Integer id);

}




