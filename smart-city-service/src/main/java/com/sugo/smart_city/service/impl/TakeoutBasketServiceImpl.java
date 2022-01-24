package com.sugo.smart_city.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.vo.TakeoutBasketGoodsItemVo;
import com.sugo.smart_city.bean.dto.TakeoutGoodsSkuDto;
import com.sugo.smart_city.bean.enums.TakeoutGoodsSkuMode;
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
    public IPage<TakeoutBasketDto> selectPage(Page<TakeoutBasketDto> basketPage, Integer userId) {
        return baseMapper.selectBasketListByUser(basketPage, userId);
    }

    @Override
    public boolean updateQuantity(Integer userId, TakeoutBasketParam takeoutBasketParam) {
        TakeoutGoods takeoutGoods = takeoutGoodsService.getById(takeoutBasketParam.getGoodsId());
        // todo 商品卖超后的商品数量
        if (takeoutGoods.getStock() != null && takeoutGoods.getStock() < takeoutBasketParam.getQuantity()){
            throw new SugoException("加购数量超出库存数");
        }

        //根据商品id查询对应的sku数据
        List<TakeoutGoodsSku> list = takeoutGoodsSkuService.list(new QueryWrapper<>(TakeoutGoodsSku.builder().goodsId(takeoutBasketParam.getGoodsId()).build()));
        if (list.size() > 0){
            List<Integer> skuIds = list.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
            if (!StringUtils.isEmpty(takeoutBasketParam.getSkuIdGroup())){
                try {
                    //json格式化id组
                    List<Integer> skuIdGroup = JSONArray.parseArray(takeoutBasketParam.getSkuIdGroup(), Integer.class);
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
                                takeoutBasketParam.setSkuIdGroup(JSONArray.toJSONString(skuIdGroup));
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
            if (!StringUtils.isEmpty(takeoutBasketParam.getSkuIdGroup())) {
                throw new SugoException("sku参数非法");
            }
        }

        TakeoutBasket takeoutBasket = mapperFacade.map(takeoutBasketParam, TakeoutBasket.class);
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
    public List<TakeoutBasketDto> list(Integer userId, Integer sellerId, Integer goodsStatus) {
        List<TakeoutBasketDto> list = baseMapper.list(userId, sellerId, goodsStatus);
        for (TakeoutBasketDto takeoutBasketDto : list) {
            List<TakeoutGoodsSkuDto> skus = takeoutBasketDto.getGoods().getSkus();
            //如果商品存在sku 则对加购数据对sku进行校验
            if (!CollectionUtils.isEmpty(skus)){
                String skuIdGroup = takeoutBasketDto.getSkuIdGroup();
                if (!StringUtils.isEmpty(skuIdGroup)){
                    try {
                        List<Integer> skuIdList = JSONArray.parseArray(skuIdGroup, Integer.class);
                        List<List<TakeoutGoodsSku>> collect = skus.stream().map(TakeoutGoodsSkuDto::getChildren).collect(Collectors.toList());
                        List<TakeoutGoodsSku> temp = new ArrayList<>();
                        for (List<TakeoutGoodsSku> takeoutGoodsSkus : collect) {
                            temp.addAll(takeoutGoodsSkus);
                        }
                        List<Integer> collect1 = temp.stream().map(TakeoutGoodsSku::getId).collect(Collectors.toList());
                        takeoutBasketDto.setSkuValid(collect1.containsAll(skuIdList));

                        //sku有效的话计算sku价格
                        if (takeoutBasketDto.getSkuValid()){
                            TakeoutBasketGoodsItemVo goods = takeoutBasketDto.getGoods();
                            double totalPrice = 0;
                            //找到价格不为0选中的sku
                            List<TakeoutGoodsSku> skuActiveList = goods.getSkus().stream().map(item -> item.getChildren().stream().filter(el -> skuIdList.contains(el.getId())).findFirst().get())
                                                                 .collect(Collectors.toList());

                            List<String> skuNameList = skuActiveList.stream().map(TakeoutGoodsSku::getName).collect(Collectors.toList());
                            takeoutBasketDto.setSkuNameList(skuNameList);

                            Optional<TakeoutGoodsSku> baseSku = skuActiveList.stream().filter(item -> item.getPrice() > 0 && item.getMode() == TakeoutGoodsSkuMode.INDEPENDENT_PRICE.getMode()).findFirst();
                            if (baseSku.isPresent()){
                                totalPrice = baseSku.get().getPrice();
                            }else {
                                totalPrice = goods.getPrice();
                            }
                            Optional<Double> reduce = skuActiveList.stream().filter(item -> item.getPrice() > 0 && item.getMode() == TakeoutGoodsSkuMode.MARK_UP_PRICE.getMode()).map(TakeoutGoodsSku::getPrice).reduce(Double::sum);
                            if (reduce.isPresent()){
                                totalPrice += reduce.get();
                            }
                            goods.setPrice(totalPrice);
                        }
                    }catch (JSONException e){
                        takeoutBasketDto.setSkuValid(false);
                        e.printStackTrace();
                    }
                }else {
                    takeoutBasketDto.setSkuValid(false);
                    //todo 加购数据异常
                }
            }else {
                takeoutBasketDto.setSkuValid(true);
            }
        }
        return list;
    }
}




