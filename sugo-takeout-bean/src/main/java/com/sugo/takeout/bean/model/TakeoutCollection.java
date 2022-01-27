package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏夹表
 * @TableName takeout_collection
 */
@TableName(value ="takeout_collection")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutCollection implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商家id
     */
    private Integer sellerId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 收藏时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}