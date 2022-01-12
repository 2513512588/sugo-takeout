package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutSellerDetailDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.mapper.TakeoutSellerMapper;
import com.sugo.smart_city.service.TakeoutDeliveryService;
import com.sugo.smart_city.service.TakeoutGoodsEvaluateService;
import com.sugo.smart_city.service.TakeoutOrderService;
import com.sugo.smart_city.service.TakeoutSellerService;
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
}




