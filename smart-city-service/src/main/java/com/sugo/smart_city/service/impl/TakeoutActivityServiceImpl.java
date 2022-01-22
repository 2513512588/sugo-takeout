package com.sugo.smart_city.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.model.TakeoutActivity;
import com.sugo.smart_city.mapper.TakeoutActivityMapper;
import com.sugo.smart_city.service.TakeoutActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutActivityServiceImpl extends ServiceImpl<TakeoutActivityMapper, TakeoutActivity>
    implements TakeoutActivityService {

    @Override
    public List<TakeoutActivity> list(Integer sellerId) {
        return baseMapper.findBySellerId(sellerId);
    }
}




