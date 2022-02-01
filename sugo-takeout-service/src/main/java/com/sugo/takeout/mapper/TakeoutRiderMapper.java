package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutRider;
import com.sugo.takeout.bean.vo.SellerOrderDeliveryVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.sugo.takeout.model.TakeoutRider
 */
public interface TakeoutRiderMapper extends BaseMapper<TakeoutRider> {

    SellerOrderDeliveryVo findByOrderCode(@Param("code") String code);
    
}




