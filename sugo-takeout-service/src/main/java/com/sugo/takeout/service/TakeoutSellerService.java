package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.dto.TakeoutSellerDetailDto;
import com.sugo.takeout.bean.model.TakeoutSeller;

/**
 * @author hehaoyang
 */
public interface TakeoutSellerService extends IService<TakeoutSeller> {

    TakeoutSellerDetailDto getDetailById(Integer id);

    TakeoutSellerDetailDto getDetailById(Integer id, String location);


}
