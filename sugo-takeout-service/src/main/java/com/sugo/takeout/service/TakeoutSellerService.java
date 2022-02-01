package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.dto.SellerDataDto;
import com.sugo.takeout.bean.dto.SellerDetailDto;
import com.sugo.takeout.bean.model.TakeoutSeller;

/**
 * @author hehaoyang
 */
public interface TakeoutSellerService extends IService<TakeoutSeller> {

    SellerDetailDto getDetailById(Integer id);

    SellerDetailDto getDetailById(Integer id, String location);

    SellerDataDto getDataById(Integer sellerId);

}
