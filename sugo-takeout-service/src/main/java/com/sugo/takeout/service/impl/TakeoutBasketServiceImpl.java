package com.sugo.takeout.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.dto.BasketDto;
import com.sugo.takeout.bean.vo.BasketGoodsItemVo;
import com.sugo.takeout.bean.dto.GoodsSkuDto;
import com.sugo.takeout.bean.enums.GoodsSkuMode;
import com.sugo.takeout.bean.model.TakeoutBasket;
import com.sugo.takeout.bean.model.TakeoutGoods;
import com.sugo.takeout.bean.model.TakeoutGoodsSku;
import com.sugo.takeout.bean.param.BasketParam;
import com.sugo.takeout.common.enums.ResultCode;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.mapper.TakeoutBasketMapper;
import com.sugo.takeout.service.TakeoutBasketService;
import com.sugo.takeout.service.TakeoutGoodsService;
import com.sugo.takeout.service.TakeoutGoodsSkuService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public IPage<BasketDto> selectPage(Page<BasketDto> basketPage, Integer userId) {
        return baseMapper.selectBasketListByUser(basketPage, userId);
    }

    @Override
    public boolean updateQuantity(Integer userId, BasketParam basketParam) {
        TakeoutGoods takeoutGoods = takeoutGoodsService.getById(basketParam.getGoodsId());
        // todo ??????????????????????????????
        if (takeoutGoods.getStock() != null && takeoutGoods.getStock() < basketParam.getQuantity()){
            throw new SugoException("???????????????????????????");
        }

        //????????????id???????????????sku??????
        List<TakeoutGoodsSku> list = takeoutGoodsSkuService.list(new QueryWrapper<>(TakeoutGoodsSku.builder().goodsId(basketParam.getGoodsId()).build()));
        if (list.size() > 0){
            List<Integer> skuIds = list.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
            if (!StringUtils.isEmpty(basketParam.getSkuIdGroup())){
                try {
                    //json?????????id???
                    List<Integer> skuIdGroup = JSONArray.parseArray(basketParam.getSkuIdGroup(), Integer.class);
                    if (skuIds.containsAll(skuIdGroup)){
                        //?????????????????????sku???????????????
                        long count = list.stream().map(TakeoutGoodsSku::getType).distinct().count();
                        // ???????????? ??????????????????sku??????????????????
                        if (count == skuIdGroup.size()){
                            // ????????????????????????skuIds????????????????????????
                            List<String> typeList = new ArrayList<>();
                            for (int skuId : skuIdGroup) {
                                @SuppressWarnings("all") // skuIds.containsAll(skuIdGroup) ???????????????
                                String type = list.stream().filter(item -> item.getId() == skuId).findFirst().get().getType();
                                typeList.add(type);
                            }
                            // typeList.stream().distinct().count() == count ???????????????????????????????????????
                            // ??????????????? ?????????????????????????????????
                            // ?????????????????????????????? ?????????????????????sku?????? ?????? ????????????????????? ?????????????????? ????????????????????????
                            if (typeList.stream().distinct().count() == count){
                                basketParam.setSkuIdGroup(JSONArray.toJSONString(skuIdGroup));
                            }else {
                                throw new SugoException("sku????????????", ResultCode.VALIDATE_FAILED);
                            }
                        }else {
                            throw new SugoException("sku????????????", ResultCode.VALIDATE_FAILED);
                        }
                    }else {
                        //???????????? ???????????????sku
                        throw new SugoException("???????????????skuId", ResultCode.VALIDATE_FAILED);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    throw new SugoException("sku??????????????????");
                }
            }else {
                //????????????sku??????
                throw new SugoException("??????sku??????", ResultCode.VALIDATE_FAILED);
            }
        }else {
            if (!StringUtils.isEmpty(basketParam.getSkuIdGroup())) {
                throw new SugoException("sku????????????");
            }
        }

        TakeoutBasket takeoutBasket = mapperFacade.map(basketParam, TakeoutBasket.class);
        takeoutBasket.setUserId(userId);
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
                //???????????????
            }catch (DuplicateKeyException e){
                e.printStackTrace();
                throw new SugoException("????????????????????????");
            }
        }
    }

    @Override
    public List<BasketDto> list(Integer userId, Integer sellerId, Integer goodsStatus) {
        List<BasketDto> list = baseMapper.list(userId, sellerId, goodsStatus);
        for (BasketDto basketDto : list) {
            List<GoodsSkuDto> skus = basketDto.getGoods().getSkus();
            //??????????????????sku ?????????????????????sku????????????
            if (!CollectionUtils.isEmpty(skus)){
                String skuIdGroup = basketDto.getSkuIdGroup();
                if (!StringUtils.isEmpty(skuIdGroup)){
                    try {
                        List<Integer> skuIdList = JSONArray.parseArray(skuIdGroup, Integer.class);
                        List<List<TakeoutGoodsSku>> collect = skus.stream().map(GoodsSkuDto::getChildren).collect(Collectors.toList());
                        List<TakeoutGoodsSku> temp = new ArrayList<>();
                        for (List<TakeoutGoodsSku> takeoutGoodsSkus : collect) {
                            temp.addAll(takeoutGoodsSkus);
                        }
                        List<Integer> collect1 = temp.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
                        basketDto.setSkuValid(collect1.containsAll(skuIdList));

                        //sku??????????????????sku??????
                        if (basketDto.getSkuValid()){
                            BasketGoodsItemVo goods = basketDto.getGoods();
                            double totalPrice = 0;
                            //??????????????????0?????????sku
                            List<TakeoutGoodsSku> skuActiveList = goods.getSkus().stream().map(item -> item.getChildren().stream().filter(el -> skuIdList.contains(el.getId())).findFirst().get())
                                                                 .collect(Collectors.toList());

                            List<String> skuNameList = skuActiveList.stream().map(TakeoutGoodsSku::getName).collect(Collectors.toList());
                            basketDto.setSkuNameList(skuNameList);

                            Optional<TakeoutGoodsSku> baseSku = skuActiveList.stream().filter(item -> item.getPrice() > 0 && item.getMode() == GoodsSkuMode.INDEPENDENT_PRICE.getMode()).findFirst();
                            if (baseSku.isPresent()){
                                totalPrice = baseSku.get().getPrice();
                            }else {
                                totalPrice = goods.getPrice();
                            }
                            Optional<Double> reduce = skuActiveList.stream().filter(item -> item.getPrice() > 0 && item.getMode() == GoodsSkuMode.MARK_UP_PRICE.getMode()).map(TakeoutGoodsSku::getPrice).reduce(Double::sum);
                            if (reduce.isPresent()){
                                totalPrice += reduce.get();
                            }
                            goods.setPrice(totalPrice);
                        }
                    }catch (JSONException e){
                        basketDto.setSkuValid(false);
                        e.printStackTrace();
                    }
                }else {
                    basketDto.setSkuValid(false);
                    //todo ??????????????????
                }
            }else {
                basketDto.setSkuValid(true);
            }
        }
        return list;
    }
}




