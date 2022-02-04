package com.sugo.takeout.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.dto.*;
import com.sugo.takeout.bean.enums.DeliveryStatus;
import com.sugo.takeout.bean.enums.OrderStatus;
import com.sugo.takeout.bean.model.*;
import com.sugo.takeout.bean.vo.BasketGoodsItemVo;
import com.sugo.takeout.bean.enums.GoodsStatus;
import com.sugo.takeout.bean.param.OrderParam;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.mapper.TakeoutCouponMapper;
import com.sugo.takeout.mapper.TakeoutOrderMapper;
import com.sugo.takeout.service.*;
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
        implements TakeoutOrderService {

    private final TakeoutBasketService takeoutBasketService;
    private final TakeoutAddressService takeoutAddressService;
    private final MapService mapService;
    private final TakeoutOrderItemService takeoutOrderItemService;
    private final TakeoutActivityService takeoutActivityService;
    private final TakeoutCouponMapper takeoutCouponMapper;
    private final TakeoutDeliveryService takeoutDeliveryService;

    @SuppressWarnings("all")
    public TakeoutOrderServiceImpl(TakeoutBasketService takeoutBasketService, TakeoutAddressService takeoutAddressService, MapService mapService, TakeoutOrderItemService takeoutOrderItemService, TakeoutActivityService takeoutActivityService, TakeoutCouponMapper takeoutCouponMapper, TakeoutDeliveryService takeoutDeliveryService) {
        this.takeoutBasketService = takeoutBasketService;
        this.takeoutAddressService = takeoutAddressService;
        this.mapService = mapService;
        this.takeoutOrderItemService = takeoutOrderItemService;
        this.takeoutActivityService = takeoutActivityService;
        this.takeoutCouponMapper = takeoutCouponMapper;
        this.takeoutDeliveryService = takeoutDeliveryService;
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
    public String createOrder(@Nullable TakeoutCoupon takeoutCoupon, TakeoutAddress takeoutAddress, TakeoutSeller takeoutSeller, OrderParam orderParam, Integer userId) {
        List<BasketDto> list = takeoutBasketService.list(userId, orderParam.getSellerId(), GoodsStatus.ON_SHELF.getStatus());
        TakeoutOrder takeoutOrder = new TakeoutOrder();
        takeoutOrder.setUserId(userId);
        takeoutOrder.setSellerId(orderParam.getSellerId());
        takeoutOrder.setConsignee(takeoutAddress.getConsignee());
        takeoutOrder.setPhone(takeoutAddress.getPhone());
        takeoutOrder.setConsigneeSex(takeoutAddress.getSex());
        takeoutOrder.setAddrName(takeoutAddress.getName());
        takeoutOrder.setAddrDetail(takeoutAddress.getAddress());
        takeoutOrder.setAddrHouseNumber(takeoutAddress.getHouseNumber());
        takeoutOrder.setAddrLat(takeoutAddress.getLat());
        takeoutOrder.setAddrLng(takeoutAddress.getLng());
        takeoutOrder.setRemark(orderParam.getRemark());
        takeoutOrder.setTablewareNum(orderParam.getTablewareNum());

        double total = 0;
        double packingFee = 0;
        //String code = ObjectId.next();
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String code = String.valueOf(snowflake.nextId());
        String addrLocation = StringUtil.formatLatLngStr(takeoutAddress.getLat() + "," + takeoutAddress.getLng());
        String sellerLocation = StringUtil.formatSellerLocation(takeoutSeller.getLocation());
        Long distance = mapService.routematrixOne(addrLocation, sellerLocation);
        double deliveryFee = takeoutAddressService.getDeliveryFee(distance);
        long deliveryTime = takeoutAddressService.getDeliveryTime(distance);

        takeoutOrder.setCode(code);
        takeoutOrder.setDeliveryFee(deliveryFee);
        takeoutOrder.setArriveTime(LocalDateTime.now().plusMinutes(deliveryTime));

        List<TakeoutOrderItem> orderItems = new ArrayList<>();

        Optional<BasketDto> hasMandatory = list.stream().filter(item -> item.getGoods().getIsMandatory()).findFirst();
        if (!hasMandatory.isPresent()) {
            throw new SugoException("没有必点品！");
        }

        for (BasketDto basketDto : list) {
            if (basketDto.getSkuValid()) {
                BasketGoodsItemVo goods = basketDto.getGoods();
                //确定库存足够
                if (goods.getStock() == null || basketDto.getQuantity() <= goods.getStock()) {
                    if (goods.getMaxPurchaseNum() != null && basketDto.getQuantity() > goods.getMaxPurchaseNum()) {
                        throw new SugoException(String.format("[%s]超出限购数量%d个", goods.getName(), goods.getMaxPurchaseNum()));
                    }
                    if (goods.getMinPurchaseNum() != null && basketDto.getQuantity() < goods.getMinPurchaseNum()) {
                        throw new SugoException(String.format("[%s]低于最低购买数量%d个", goods.getName(), goods.getMinPurchaseNum()));
                    }
                    TakeoutOrderItem takeoutOrderItem = new TakeoutOrderItem();
                    takeoutOrderItem.setSkuNameGroup(org.apache.commons.lang3.StringUtils.join(basketDto.getSkuNameList(), "、"));
                    takeoutOrderItem.setOrderCode(code);
                    takeoutOrderItem.setGoodsId(basketDto.getGoodsId());
                    takeoutOrderItem.setQuantity(basketDto.getQuantity());
                    takeoutOrderItem.setTotal((goods.getPackingFee() + goods.getPrice()) * basketDto.getQuantity());
                    orderItems.add(takeoutOrderItem);

                    packingFee += goods.getPackingFee() * basketDto.getQuantity();
                    total += takeoutOrderItem.getTotal();
                } else if (basketDto.getQuantity() > goods.getStock()) {
                    throw new SugoException(String.format("[%s]结算数量超出限制", goods.getName()));
                }
            }
        }

        if (total < takeoutSeller.getMinDeliveryPrice()) {
            throw new SugoException("不满起送金额");
        }

        //满减活动条件总价
        double totalPriceCondition = total;
        // 满减
        //活动价格
        total = takeoutActivityService.reduction(orderParam.getSellerId(), totalPriceCondition);

        // 活动减免金额
        if (totalPriceCondition > total) {
            takeoutOrder.setActivityDiscount(totalPriceCondition - total);
        }

        //使用红包
        if (takeoutCoupon != null) {
            if (totalPriceCondition >= takeoutCoupon.getCondition()) {
                int used = takeoutCouponMapper.useCoupon(userId, takeoutCoupon.getId());
                if (used == 1) {
                    total -= takeoutCoupon.getPrice();
                    takeoutOrder.setCouponDiscount(takeoutCoupon.getPrice());
                } else {
                    throw new SugoException("代金劵状态异常");
                }
            }
        }

        takeoutOrder.setTotal(total + deliveryFee);
        takeoutOrder.setPackingFee(packingFee);

        int insert = baseMapper.insert(takeoutOrder);
        if (insert != 1) {
            throw new SugoException("结算异常");
        }
        for (TakeoutOrderItem orderItem : orderItems) {
            boolean save = takeoutOrderItemService.save(orderItem);
            if (!save) {
                throw new SugoException("结算异常");
            }
        }
        // 清除购物车
        boolean remove = takeoutBasketService.removeByIds(list.stream().map(BasketDto::getId).collect(Collectors.toList()));
        if (!remove) {
            throw new SugoException("结算异常");
        }

        String result = paymentService.create(code, String.valueOf(total), "http://localhost:8082/#/pages/order/order", 3);

        RedisUtil.set(code, result, 1000 * 60 * 15);
        //todo 定时任务 15分钟关闭订单
        // todo 骑手获取订单时要判断是否在配送范围时间内
        // todo 判断是否超出配送范围
        return code;
    }

    @Override
    public IPage<OrderListDto> getList(Page<TakeoutOrder> page, Integer userId, @Nullable Integer[] statuses) {
        return baseMapper.list(page, userId, statuses);
    }

    @Override
    public OrderDetailDto getDetail(Integer userId, String orderCode) {
        return baseMapper.getDetail(userId, orderCode);
    }

    @Transactional(rollbackFor = SugoException.class)
    @Override
    public void receiveOrder(Integer riderId, String orderCode) {
        TakeoutDelivery takeoutDelivery = takeoutDeliveryService.getLastDeliveryByOrderCode(orderCode);
        takeoutDelivery.setRiderStatus(DeliveryStatus.RECEIVED_ORDER.getStatus());
        takeoutDelivery.setRiderId(riderId);
        boolean insert = takeoutDeliveryService.save(takeoutDelivery);
        if (!insert){
            throw new SugoException("接单失败！");
        }
        int update = baseMapper.receiveOrder(riderId, orderCode);
        if (update != 1){
            throw new SugoException("订单已被别人抢走了");
        }
    }

    @Override
    public IPage<String> getRiderOrderCodeList(Page<?> page) {
        return baseMapper.getAllRiderOrderCodeList(page);
    }

    @Override
    public IPage<AcceptedRiderOrderDto> getAcceptedRiderOrderList(Page<?> page, Integer riderId, Integer status) {
        @SuppressWarnings("unused")
        IPage<AcceptedRiderOrderDto> acceptedRiderOrderList = baseMapper.getAcceptedRiderOrderList(page, riderId, status);
        List<AcceptedRiderOrderDto> records = acceptedRiderOrderList.getRecords();
        for (AcceptedRiderOrderDto record : records) {
//            LocalDateTime acceptOrderTime = takeoutDeliveryMapper.getAcceptOrderTime(record.getCode());
            record.setSellerLatLng(StringUtil.parseSellerLocation(record.getSellerLatLng()));
        }
        acceptedRiderOrderList.setRecords(records);
        return acceptedRiderOrderList;
    }

    @Override
    public IPage<SellerOrderDto> getSellerOrderList(Page<TakeoutOrder> page, Integer sellerId, Integer status) {
        return baseMapper.getSellerOrderList(page, sellerId, status);
    }

    @Override
    public void paySucess(String code) {
        int update = baseMapper.updateOrderStatus(code, OrderStatus.PAID.getStatus());
        if (update == 1){
            TakeoutDelivery takeoutDelivery = new TakeoutDelivery();
            takeoutDelivery.setOrderCode(code);
            boolean save = takeoutDeliveryService.save(takeoutDelivery);
            if (!save){
                //todo 系统异常处理 支付成功呀 物流没有添加成功
                throw new SugoException("系统异常");
            }
        }else {
            throw new SugoException("订单已支付成功！");
        }
    }

}




