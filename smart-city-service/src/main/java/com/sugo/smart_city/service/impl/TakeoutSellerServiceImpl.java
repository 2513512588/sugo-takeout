package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutSellerDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.common.aspect.annotation.StartPage;
import com.sugo.smart_city.mapper.TakeoutSellerMapper;
import com.sugo.smart_city.service.TakeoutSellerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutSellerServiceImpl extends ServiceImpl<TakeoutSellerMapper, TakeoutSeller>
    implements TakeoutSellerService{

    @StartPage
    @Override
    public Object selectPage(IPage<TakeoutSellerDto> takeoutSellerPage, TakeoutSeller takeoutSeller) {
        TakeoutSeller queryWrap = new TakeoutSeller();
        queryWrap.setProvince(takeoutSeller.getProvince());
        queryWrap.setCity(takeoutSeller.getCity());
        queryWrap.setName(takeoutSeller.getName());
        queryWrap.setTypeId(takeoutSeller.getTypeId());
        List<TakeoutSellerDto> takeoutSellerDtos = baseMapper.selectDtoPage(takeoutSellerPage, queryWrap);
        takeoutSellerPage.setRecords(takeoutSellerDtos);
        return takeoutSellerPage;
    }
}




