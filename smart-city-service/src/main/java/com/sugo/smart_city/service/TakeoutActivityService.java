package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutActivity;

import java.util.List;

/**
 *
 */
public interface TakeoutActivityService extends IService<TakeoutActivity> {

    List<TakeoutActivity> list(Integer sellerId);

}
