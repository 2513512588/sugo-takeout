package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutSellerDetailDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.common.enums.ResultCode;
import com.sugo.smart_city.common.exception.SugoException;
import com.sugo.smart_city.common.util.StringUtil;
import com.sugo.smart_city.mapper.TakeoutSellerMapper;
import com.sugo.smart_city.service.*;
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
    public TakeoutSellerDetailDto getDetailById(Integer id) {
        TakeoutSellerDetailDto detail = baseMapper.getDetailById(id);
        Integer avgMonthOrderNumBySeller = takeoutOrderService.getAvgMonthOrderNumBySeller(id);
        detail.setMonthOrderNum(avgMonthOrderNumBySeller);
        Double avgScoreBySeller = takeoutGoodsEvaluateService.getAvgScoreBySeller(id);
        detail.setScore(avgScoreBySeller);
        Long avgDeliveryTimeBySeller = takeoutDeliveryService.getAvgDeliveryTimeBySeller(id);
        if (StringUtils.isEmpty(avgDeliveryTimeBySeller)){
            avgDeliveryTimeBySeller = 0L;
        }
        detail.setAvgDeliveryTime(avgDeliveryTimeBySeller);
        return detail;
    }

    @Override
    public TakeoutSellerDetailDto getDetailById(Integer id, String location) {
        TakeoutSellerDetailDto detail = getDetailById(id);
        if (!StringUtils.isEmpty(location)){
            String myLocation = StringUtil.formatLatLngStr(location);
            String sellerLocation = StringUtil.parseSellerLocation(detail.getLocation());
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
}




