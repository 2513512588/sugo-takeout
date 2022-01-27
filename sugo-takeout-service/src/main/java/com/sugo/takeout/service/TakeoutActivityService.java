package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.model.TakeoutActivity;

import java.util.List;

/**
 * @author hehaoyang
 */
public interface TakeoutActivityService extends IService<TakeoutActivity> {

    List<TakeoutActivity> list(Integer sellerId);

    /**
     * 计算最优惠价格
     * @param sellerId 店铺id
     * @param total 总金额
     * @return 减免后的金额
     */
    double reduction(Integer sellerId, double total);

}
