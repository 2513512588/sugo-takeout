package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.dto.SellerDataDto;
import com.sugo.takeout.bean.dto.SellerDetailDto;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.common.enums.ResultCode;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.common.util.StringUtil;
import com.sugo.takeout.mapper.TakeoutSellerMapper;
import com.sugo.takeout.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author hehaoyang
 */
@Service
@AllArgsConstructor
public class TakeoutSellerServiceImpl extends ServiceImpl<TakeoutSellerMapper, TakeoutSeller>
    implements TakeoutSellerService{

    private TakeoutOrderService takeoutOrderService;
    private TakeoutGoodsEvaluateService takeoutGoodsEvaluateService;
    private TakeoutDeliveryService takeoutDeliveryService;
    private MapService mapService;

    @Override
    public SellerDetailDto getDetailById(Integer id) {
        SellerDetailDto detail = baseMapper.getDetailById(id);
        Integer avgMonthOrderNumBySeller = takeoutOrderService.getAvgMonthOrderNumBySeller(id);
        detail.setMonthOrderNum(avgMonthOrderNumBySeller);
        Double avgScoreBySeller = takeoutGoodsEvaluateService.getAvgScoreBySeller(id);
        detail.setScore(avgScoreBySeller);
        double avgDeliveryTimeBySeller = takeoutDeliveryService.getAvgDeliveryTimeBySeller(id);
        if (StringUtils.isEmpty(avgDeliveryTimeBySeller)){
            avgDeliveryTimeBySeller = 0L;
        }
        detail.setAvgDeliveryTime(avgDeliveryTimeBySeller);
        return detail;
    }

    @Override
    public SellerDetailDto getDetailById(Integer id, String location) {
        SellerDetailDto detail = getDetailById(id);
        if (!StringUtils.isEmpty(location)){
            String myLocation = StringUtil.formatLatLngStr(location);
            String sellerLocation = StringUtil.formatSellerLocation(detail.getLocation());
            Long distance = mapService.routematrixOne(myLocation, sellerLocation);
            //todo 配送价格表 每公里x，最低，最高
            @SuppressWarnings("all")
            double price = distance / 1000 * 2;
            double max = 5;
            double min = 2;
            detail.setDeliveryFee(Math.max(Math.min(max, price), min));
        }else {
            throw new SugoException("请传入位置信息", ResultCode.VALIDATE_FAILED);
        }
        return detail;
    }

    @Override
    public SellerDataDto getDataById(Integer sellerId) {
        return baseMapper.getDataById(sellerId);
    }
}




