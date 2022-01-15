package com.sugo.smart_city.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.bean.param.TakeoutBasketParam;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.mapper.TakeoutBasketMapper;
import com.sugo.smart_city.service.TakeoutBasketService;
import com.sugo.smart_city.service.TakeoutGoodsService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
@AllArgsConstructor
public class TakeoutBasketServiceImpl extends ServiceImpl<TakeoutBasketMapper, TakeoutBasket>
    implements TakeoutBasketService {

    private final MapperFacade mapperFacade;
    private final TakeoutGoodsService takeoutGoodsService;

    @Override
    public IPage<TakeoutBasketDto> selectPage(Page<TakeoutBasketDto> basketPage, Integer userId) {
        return baseMapper.selectBasketListByUser(basketPage, userId);
    }

    @Override
    public boolean updateQuantity(Integer userId, TakeoutBasketParam takeoutBasketParam) {
        TakeoutBasket takeoutBasket = mapperFacade.map(takeoutBasketParam, TakeoutBasket.class);
        takeoutBasket.setUserId(userId);
        TakeoutGoods takeoutGoods = takeoutGoodsService.getById(takeoutBasket.getGoodsId());
        takeoutBasket.setSellerId(takeoutGoods.getSellerId());
        QueryWrapper<TakeoutBasket> queryWrapper = new QueryWrapper<>(
                TakeoutBasket.builder().userId(userId).goodsId(takeoutBasket.getGoodsId()).skuId(takeoutBasket.getSkuId()).build()
        );
        Long count = baseMapper.selectCount(queryWrapper);
        //不存在记录
        if (count == 0){
            if (takeoutBasket.getQuantity() == 0){
                return true;
            }else if (takeoutBasket.getQuantity() > 0){
                return baseMapper.insert(takeoutBasket) == 1;
            }
        //存在记录
        }else if (count == 1){
            if (takeoutBasket.getQuantity() == 0){
                return baseMapper.delete(queryWrapper) == 1;
            }else if (takeoutBasket.getQuantity() > 0){
                return baseMapper.update(takeoutBasket, queryWrapper) == 1;
            }
        //数据有问题
        }else if (count > 1){
            //todo 数据错误 应该记录通过日志记录下来
            throw new SysException("系统数据错误");
        }
        return false;
    }
}




