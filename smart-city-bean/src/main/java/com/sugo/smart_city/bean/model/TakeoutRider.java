package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 外卖骑手表
 * @TableName takeout_rider
 */
@TableName(value ="takeout_rider")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutRider implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 骑手所在市
     */
    private String city;

    /**
     * 骑手所在省
     */
    private String province;

    /**
     * 骑手联系电话
     */
    private String phone;

    /**
     * 在线状态 1 在线 2 离线
     */
    private Integer status;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证正面照片
     */
    private String idcardPicPositive;

    /**
     * 身份证反面照片
     */
    private String idcardPicInpositive;

    /**
     * 用户id
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}