package com.sugo.smart_city.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.model.TakeoutBasket;
import com.sugo.smart_city.bean.model.TakeoutGoods;
import com.sugo.smart_city.bean.model.TakeoutGoodsSku;
import com.sugo.smart_city.bean.param.TakeoutBasketParam;
import com.sugo.smart_city.common.enums.ResultCode;
import com.sugo.smart_city.common.exception.SugoException;
import com.sugo.smart_city.mapper.TakeoutBasketMapper;
import com.sugo.smart_city.service.TakeoutBasketService;
import com.sugo.smart_city.service.TakeoutGoodsService;
import com.sugo.smart_city.service.TakeoutGoodsSkuService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 */
@Service
@AllArgsConstructor
public class TakeoutBasketServiceImpl extends ServiceImpl<TakeoutBasketMapper, TakeoutBasket>
    implements TakeoutBasketService {

    private final MapperFacade mapperFacade;
    private final TakeoutGoodsService takeoutGoodsService;
    private final TakeoutGoodsSkuService takeoutGoodsSkuService;

    @Override
    public IPage<TakeoutBasketDto> selectPage(Page<TakeoutBasketDto> basketPage, Integer userId) {
        return baseMapper.selectBasketListByUser(basketPage, userId);
    }

    @Override
    public boolean updateQuantity(Integer userId, TakeoutBasketParam takeoutBasketParam) {
        List<TakeoutGoodsSku> list = takeoutGoodsSkuService.list(new QueryWrapper<>(TakeoutGoodsSku.builder().goodsId(takeoutBasketParam.getGoodsId()).build()));
        if (list.size() > 0){
            List<Integer> skuIds = list.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
            if (!StringUtils.isEmpty(takeoutBasketParam.getSkuIdGroup())){
                //json格式化id组
                List<Integer> skuIdGroup = JSONArray.parseArray(takeoutBasketParam.getSkuIdGroup(), Integer.class);
                if (skuIds.containsAll(skuIdGroup)){
                    takeoutBasketParam.setSkuIdGroup(JSONArray.toJSONString(skuIdGroup));
                }else {
                    //参数错误 没有对应的sku
                    throw new SugoException("没有对应的skuId", ResultCode.VALIDATE_FAILED);
                }
            }else {
                //没有提交sku参数
                throw new SugoException("缺少sku参数", ResultCode.VALIDATE_FAILED);
            }
        }

        TakeoutBasket takeoutBasket = mapperFacade.map(takeoutBasketParam, TakeoutBasket.class);
        takeoutBasket.setUserId(userId);
        TakeoutGoods takeoutGoods = takeoutGoodsService.getById(takeoutBasket.getGoodsId());
        takeoutBasket.setSellerId(takeoutGoods.getSellerId());

        TakeoutBasket param = TakeoutBasket.builder().userId(userId).goodsId(takeoutBasket.getGoodsId()).build();
        if (!StringUtils.isEmpty(takeoutBasket.getSkuIdGroup())){
            param.setSkuIdGroup(takeoutBasket.getSkuIdGroup());
        }
        QueryWrapper<TakeoutBasket> queryWrapper = new QueryWrapper<>(param);
        if (takeoutBasket.getQuantity() == 0){
            return baseMapper.delete(queryWrapper) == 1;
        }else {
            try {
                return saveOrUpdate(takeoutBasket, queryWrapper);
                //点击太快了
            }catch (DuplicateKeyException e){
                e.printStackTrace();
                throw new SugoException("小主慢点操作哦！");
            }
        }
    }

    @Override
    public List<TakeoutBasketDto> list(Integer userId, Integer sellerId) {
        return baseMapper.list(userId, sellerId);
    }
}




