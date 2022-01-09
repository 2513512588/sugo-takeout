package com.sugo.smart_city.bean.param;

import com.sugo.smart_city.common.valid.Groups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TakeoutSellerAddParam implements Serializable {

    /**
     * 店铺类型id
     */
    @ApiModelProperty("店铺类型id")
    @NotNull(message = "店铺类型id不能为空", groups = Groups.Add.class)
    private Integer typeId;
    /**
     * 店铺logo
     */
    @ApiModelProperty("店铺logo")
    @NotBlank(message = "店铺logo不能为空", groups = Groups.Add.class)
    private String avatar;
    /**
     * 店铺名称
     */
    @ApiModelProperty("店铺名称")
    @NotBlank(message = "店铺名称不能为空", groups = Groups.Add.class)
    private String name;
    /**
     * 工商备案名称
     */
    @ApiModelProperty("工商备案名称")
    @NotBlank(message = "工商备案名称不能为空", groups = Groups.Add.class)
    private String enterpriseName;

    /**
     * 工商备案地址
     */
    @ApiModelProperty("工商备案地址")
    @NotBlank(message = "工商备案地址不能为空", groups = Groups.Add.class)
    private String enterpriseAddress;

    /**
     * 营业执照照片
     */
    @ApiModelProperty("营业执照照片")
    @NotBlank(message = "营业执照照片不能为空", groups = Groups.Add.class)
    private String enterprisePic;
    /**
     * 商家所在市
     */
    @ApiModelProperty("商家所在市")
    @NotBlank(message = "当前位置城市不能为空", groups = Groups.Add.class)
    private String city;

    /**
     * 商家所在省份
     */
    @ApiModelProperty("商家所在省份")
    @NotBlank(message = "当前位置省份不能为空", groups = Groups.Add.class)
    private String province;

}
