package com.sugo.smart_city.service.impl;

import cn.hutool.core.lang.ObjectId;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutBasketDto;
import com.sugo.smart_city.bean.dto.TakeoutOrderListDto;
import com.sugo.smart_city.bean.vo.TakeoutBasketGoodsItemVo;
import com.sugo.smart_city.bean.enums.TakeoutGoodsStatus;
import com.sugo.smart_city.bean.model.*;
import com.sugo.smart_city.bean.param.TakeoutOrderParam;
import com.sugo.smart_city.common.exception.SugoException;
import com.sugo.smart_city.common.util.RedisUtil;
import com.sugo.smart_city.common.util.StringUtil;
import com.sugo.smart_city.mapper.TakeoutOrderMapper;
import com.sugo.smart_city.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TakeoutOrderServiceImpl extends ServiceImpl<TakeoutOrderMapper, TakeoutOrder>
    implements TakeoutOrderService{

    private final TakeoutBasketService takeoutBasketService;
    private final TakeoutAddressService takeoutAddressService;
    private final MapService mapService;
    private final TakeoutOrderItemService takeoutOrderItemService;
    private final TakeoutActivityService takeoutActivityService;

    @Autowired
    public TakeoutOrderServiceImpl(TakeoutBasketService takeoutBasketService, TakeoutAddressService takeoutAddressService, MapService mapService, TakeoutOrderItemService takeoutOrderItemService, TakeoutActivityService takeoutActivityService) {
        this.takeoutBasketService = takeoutBasketService;
        this.takeoutAddressService = takeoutAddressService;
        this.mapService = mapService;
        this.takeoutOrderItemService = takeoutOrderItemService;
        this.takeoutActivityService = takeoutActivityService;
    }

    @Autowired
    @Qualifier("alipayServiceImpl")
    private PaymentService paymentService;

    @Override
    public double getAvgCostBySeller(Integer sellerId) {
        return baseMapper.getAvgCostBySeller(sellerId);
    }

    @Override
    public List<Double> getAvgCostBySellerList(List<Integer> sellerIds) {
        return baseMapper.getAvgCostBySellerList(sellerIds);
    }

    @Override
    public Integer getAvgMonthOrderNumBySeller(Integer sellerId) {
        return baseMapper.getAvgMonthOrderNumBySeller(sellerId);
    }

    @Override
    public List<Integer> getAvgMonthOrderNumBySellerList(List<Integer> sellerIds) {
        return baseMapper.getAvgMonthOrderNumBySellerList(sellerIds);
    }

    @Override
    public double getTotalPriceByUserAndSeller(Integer userId, Integer sellerId) {
        return baseMapper.getTotalPriceByUserAndSeller(userId, sellerId);
    }

    @Transactional(rollbackFor = SugoException.class)
    @Override
    public String createOrder(@Nullable TakeoutCoupon takeoutCoupon, TakeoutAddress takeoutAddress, TakeoutSeller takeoutSeller, TakeoutOrderParam takeoutOrderParam, Integer userId) {
        List<TakeoutBasketDto> list = takeoutBasketService.list(userId, takeoutOrderParam.getSellerId(), TakeoutGoodsStatus.ON_SHELF.getStatus());
        TakeoutOrder takeoutOrder = new TakeoutOrder();
        takeoutOrder.setUserId(userId);
        takeoutOrder.setSellerId(takeoutOrderParam.getSellerId());
        takeoutOrder.setConsignee(takeoutAddress.getConsignee());
        takeoutOrder.setPhone(takeoutAddress.getPhone());
        takeoutOrder.setConsigneeSex(takeoutAddress.getSex());
        takeoutOrder.setAddrName(takeoutAddress.getName());
        takeoutOrder.setAddrDetail(takeoutAddress.getAddress());
        takeoutOrder.setAddrHouseNumber(takeoutAddress.getHouseNumber());
        takeoutOrder.setAddrLat(takeoutAddress.getLat());
        takeoutOrder.setAddrLng(takeoutAddress.getLng());
        takeoutOrder.setRemark(takeoutOrderParam.getRemark());
        takeoutOrder.setTablewareNum(takeoutOrderParam.getTablewareNum());

        double total = 0;
        double packingFee = 0;
        String code = ObjectId.next();
        String addrLocation = StringUtil.formatLatLngStr(takeoutAddress.getLat() + "," + takeoutAddress.getLng());
        String sellerLocation = StringUtil.parseSellerLocation(takeoutSeller.getLocation());
        Long distance = mapService.routematrixOne(addrLocation, sellerLocation);
        double deliveryFee = takeoutAddressService.getDeliveryFee(distance);
        long deliveryTime = takeoutAddressService.getDeliveryTime(distance);

        takeoutOrder.setCode(code);
        takeoutOrder.setDeliveryFee(deliveryFee);
        takeoutOrder.setArriveTime(LocalDateTime.now().plusMinutes(deliveryTime));

        List<TakeoutOrderItem> orderItems = new ArrayList<>();

        Optional<TakeoutBasketDto> hasMandatory = list.stream().filter(item -> item.getGoods().getIsMandatory()).findFirst();
        if (!hasMandatory.isPresent()){
            throw new SugoException("没有必点品！");
        }

        for (TakeoutBasketDto takeoutBasketDto : list) {
            if (takeoutBasketDto.getSkuValid()) {
                TakeoutBasketGoodsItemVo goods = takeoutBasketDto.getGoods();
                //确定库存足够
                if (goods.getStock() == null || takeoutBasketDto.getQuantity() <= goods.getStock()){
                    if (goods.getMaxPurchaseNum() != null && takeoutBasketDto.getQuantity() > goods.getMaxPurchaseNum()) {
                        throw new SugoException(String.format("[%s]超出限购数量%d个", goods.getName(), goods.getMaxPurchaseNum()));
                    }
                    if (goods.getMinPurchaseNum() != null && takeoutBasketDto.getQuantity() < goods.getMinPurchaseNum()){
                        throw new SugoException(String.format("[%s]低于最低购买数量%d个", goods.getName(), goods.getMinPurchaseNum()));
                    }
                    TakeoutOrderItem takeoutOrderItem = new TakeoutOrderItem();
                    takeoutOrderItem.setSkuNameGroup(org.apache.commons.lang3.StringUtils.join(takeoutBasketDto.getSkuNameList(), "、"));
                    takeoutOrderItem.setOrderCode(code);
                    takeoutOrderItem.setGoodsId(takeoutBasketDto.getGoodsId());
                    takeoutOrderItem.setQuantity(takeoutBasketDto.getQuantity());
                    takeoutOrderItem.setTotal((goods.getPackingFee() + goods.getPrice()) * takeoutBasketDto.getQuantity());
                    orderItems.add(takeoutOrderItem);

                    packingFee += goods.getPackingFee() * takeoutBasketDto.getQuantity();
                    total += takeoutOrderItem.getTotal();
                }else if (takeoutBasketDto.getQuantity() > goods.getStock()){
                    throw new SugoException(String.format("[%s]结算数量超出限制", goods.getName()));
                }
            }
        }

        if (total < takeoutSeller.getMinDeliveryPrice()){
            throw new SugoException("不满起送金额");
        }

        //满减活动条件总价
        double totalPriceCondition = total;
        // 满减
        //活动价格
        total = takeoutActivityService.reduction(takeoutOrderParam.getSellerId(), totalPriceCondition);

        //使用红包
        if (takeoutCoupon != null){
            if (totalPriceCondition >= takeoutCoupon.getCondition()){
                total -= takeoutCoupon.getPrice();
            }
        }

        takeoutOrder.setTotal(total + deliveryFee);
        takeoutOrder.setPackingFee(packingFee);

        int insert = baseMapper.insert(takeoutOrder);
        if (insert != 1){
            throw new SugoException("结算异常");
        }
        for (TakeoutOrderItem orderItem : orderItems) {
            boolean save = takeoutOrderItemService.save(orderItem);
            if (!save){
                throw new SugoException("结算异常");
            }
        }
        // 清除购物车
        boolean remove = takeoutBasketService.removeByIds(list.stream().map(TakeoutBasketDto::getId).collect(Collectors.toList()));
        if (!remove){
            throw new SugoException("结算异常");
        }

        String result = paymentService.create(code, String.valueOf(total), "", 1);

        RedisUtil.set(code, result, 1000 * 60 * 15);
        //todo 定时任务 15分钟关闭订单
        // todo 骑手获取订单时要判断是否在配送范围时间内
        // todo 判断是否超出配送范围
        return code;
    }

    @Override
    public IPage<TakeoutOrderListDto> getList(IPage<TakeoutOrder> page, Integer userId, @Nullable Integer[] statuses) {
        return baseMapper.list(page, userId, statuses);
    }

}




