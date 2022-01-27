package com.sugo.takeout.bean.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 优惠卷数据展示
 * @author hehaoyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeoutCouponDto implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 优惠卷金额
     */
    private BigDecimal price;

    /**
     * 使用条件金额
     */
    @TableField("`condition`")
    private BigDecimal condition;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;


}