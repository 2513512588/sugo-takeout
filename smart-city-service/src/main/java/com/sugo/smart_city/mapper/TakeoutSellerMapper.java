package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.bean.dto.TakeoutSellerDto;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutSeller
 */
public interface TakeoutSellerMapper extends BaseMapper<TakeoutSeller> {

    List<TakeoutSellerDto> selectDtoPage(IPage<?> page, @Param("entity") TakeoutSeller queryWrapper);

}




