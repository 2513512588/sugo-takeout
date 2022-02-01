package com.sugo.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.dto.BasketDto;
import com.sugo.takeout.bean.model.TakeoutBasket;
import com.sugo.takeout.bean.param.BasketParam;

import java.util.List;

/**
 *
 */
public interface TakeoutBasketService extends IService<TakeoutBasket> {

    IPage<BasketDto> selectPage(Page<BasketDto> basketPage, Integer userId);

    /**
     * 修改购物车加购数量
     */
    boolean updateQuantity(Integer userId, BasketParam basketParam);

    /**
     * @param userId 用户id
     * @param sellerId 卖家id
     * @param goodsStatus 商品状态
     * @return 购物车dto数据
     */
    List<BasketDto> list(Integer userId, Integer sellerId, Integer goodsStatus);
}
