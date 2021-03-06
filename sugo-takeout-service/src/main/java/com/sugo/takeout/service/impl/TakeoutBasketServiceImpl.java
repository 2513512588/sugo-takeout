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
        // todo 商品卖超后的商品数量
        if (takeoutGoods.getStock() != null && takeoutGoods.getStock() < basketParam.getQuantity()){
            throw new SugoException("加购数量超出库存数");
        }

        //根据商品id查询对应的sku数据
        List<TakeoutGoodsSku> list = takeoutGoodsSkuService.list(new QueryWrapper<>(TakeoutGoodsSku.builder().goodsId(basketParam.getGoodsId()).build()));
        if (list.size() > 0){
            List<Integer> skuIds = list.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
            if (!StringUtils.isEmpty(basketParam.getSkuIdGroup())){
                try {
                    //json格式化id组
                    List<Integer> skuIdGroup = JSONArray.parseArray(basketParam.getSkuIdGroup(), Integer.class);
                    if (skuIds.containsAll(skuIdGroup)){
                        //找出该商品所有sku的分类数量
                        long count = list.stream().map(TakeoutGoodsSku::getType).distinct().count();
                        // 初步校验 提交的数量和sku分类数量一致
                        if (count == skuIdGroup.size()){
                            // 过滤出用户提交的skuIds数据中有哪些分类
                            List<String> typeList = new ArrayList<>();
                            for (int skuId : skuIdGroup) {
                                @SuppressWarnings("all") // skuIds.containsAll(skuIdGroup) 已经判断了
                                String type = list.stream().filter(item -> item.getId() == skuId).findFirst().get().getType();
                                typeList.add(type);
                            }
                            // typeList.stream().distinct().count() == count 说明同一个分类下只选了一个
                            // 总数量一致 且没有分类提交了重复的
                            // 如果要有分类多选的话 应该遍历商品的sku分类 判断 提交上来的分类 单选的有一个 多选的至少有一个
                            if (typeList.stream().distinct().count() == count){
                                basketParam.setSkuIdGroup(JSONArray.toJSONString(skuIdGroup));
                            }else {
                                throw new SugoException("sku参数错误", ResultCode.VALIDATE_FAILED);
                            }
                        }else {
                            throw new SugoException("sku参数错误", ResultCode.VALIDATE_FAILED);
                        }
                    }else {
                        //参数错误 没有对应的sku
                        throw new SugoException("没有对应的skuId", ResultCode.VALIDATE_FAILED);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    throw new SugoException("sku参数格式有误");
                }
            }else {
                //没有提交sku参数
                throw new SugoException("缺少sku参数", ResultCode.VALIDATE_FAILED);
            }
        }else {
            if (!StringUtils.isEmpty(basketParam.getSkuIdGroup())) {
                throw new SugoException("sku参数非法");
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
                //点击太快了
            }catch (DuplicateKeyException e){
                e.printStackTrace();
                throw new SugoException("小主慢点操作哦！");
            }
        }
    }

    @Override
    public List<BasketDto> list(Integer userId, Integer sellerId, Integer goodsStatus) {
        List<BasketDto> list = baseMapper.list(userId, sellerId, goodsStatus);
        for (BasketDto basketDto : list) {
            List<GoodsSkuDto> skus = basketDto.getGoods().getSkus();
            //如果商品存在sku 则对加购数据对sku进行校验
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

                        //sku有效的话计算sku价格
                        if (basketDto.getSkuValid()){
                            BasketGoodsItemVo goods = basketDto.getGoods();
                            double totalPrice = 0;
                            //找到价格不为0选中的sku
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
                    //todo 加购数据异常
                }
            }else {
                basketDto.setSkuValid(true);
            }
        }
        return list;
    }
}




