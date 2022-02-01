package com.sugo.takeout.bean.param;


import com.sugo.takeout.common.valid.Groups;
import com.sugo.takeout.common.valid.annotation.ListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;


@Data
public class AddressParam implements Serializable {

    /**
     * 地址id
     */
    @NotNull(groups = Groups.Update.class, message = "地址id不能为空")
    @Null(groups = Groups.Add.class)
    private Integer id;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    @Length(max = 40)
    private String consignee;

    /**
     * 收货人手机号
     */
    @NotBlank(message = "收货人手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号不正确")
    private String phone;


    /**
     * 收货地址名称
     */
    @NotBlank(message = "收货地址名称不能为空")
    @Length(max = 60)
    private String name;

    /**
     * 收货详细地址
     */
    @NotBlank(message = "收货详细地址不能为空")
    @Length(max = 60)
    private String address;


    /**
     * 门牌号
     */
    @NotBlank(message = "门牌号不能为空")
    @Length(max = 50)
    private String houseNumber;


    /**
     * 标记
     */
    @Length(max = 10)
    private String marker;


    /**
     * 性别 1 男 2女
     */
    @ListValue(message = "性别应指定相应的值" , vals = {1,2})
    private Integer sex;

    /**
     * 纬度
     */
    @Pattern(regexp = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,10})?|90(\\.0{1,10})?)$", message = "纬度格式不正确")
    @NotBlank(message = "纬度不能为空")
    @Length(max = 40)
    private String lat;

    /**
     * 经度
     */
    @Pattern(regexp = "^[\\-\\+]?(0(\\.\\d{1,10})?|([1-9](\\d)?)(\\.\\d{1,10})?|1[0-7]\\d{1}(\\.\\d{1,10})?|180\\.0{1,10})$", message = "经度格式不正确")
    @NotBlank(message = "经度不能为空")
    @Length(max = 40)
    private String lng;


}