package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutActivity;

import java.util.List;

/**
 * @Entity com.sugo.takeout.model.TakeoutActivity
 */
public interface TakeoutActivityMapper extends BaseMapper<TakeoutActivity> {

    List<TakeoutActivity> findBySellerId(Integer id);

}




