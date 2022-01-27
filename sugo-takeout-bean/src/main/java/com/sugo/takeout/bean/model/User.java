package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sugo.takeout.common.valid.Groups;
import com.sugo.takeout.common.valid.annotation.ListValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 主键id
     */
    @NotBlank(message = "用户id不能为空", groups = Groups.Update.class)
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名需要为3-50个字符之间")
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户绑定手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号不正确")
    @ApiModelProperty("用户绑定手机号")
    private String phone;

    /**
     * 用户绑定的邮箱
     */
    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("用户绑定的邮箱")
    private String email;

    /**
     * 用户性别 1 男 2 女
     */
    @ListValue(message = "性别应指定相应的值" , vals = {1,2})
    @ApiModelProperty("用户性别")
    private Integer sex;

    /**
     * 用户状态 1 正常使用 2 封禁 3 审核中
     */
    @ApiModelProperty("用户状态 1 正常使用 2 封禁 3 审核中")
    private Integer status;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 用户数据更新时间
     */
    @ApiModelProperty("用户数据更新时间")
    private LocalDateTime gmtUpdate;

    /**
     * 用户是否删除
     */
    @ApiModelProperty("用户是否删除")
    private Boolean isDeleted;

    /**
     * 用户类型 1 普通用户 2 商家 3骑手
     */
    @ApiModelProperty("用户类型 1 普通用户 2 商家 3骑手")
    private Integer roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}