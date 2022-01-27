package com.sugo.takeout.bean.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
* 用户角色表
* @TableName role
*/
@Data
public class Role implements Serializable {

    /**
    * 主键id
    */
    @ApiModelProperty("主键id")
    private Integer id;
    /**
    * 角色名字
    */
    @Size(max= 30, message="编码长度不能超过30")
    @ApiModelProperty("角色名字")
    private String name;
    /**
    * 备注
    */
    @Size(max= 50, message="编码长度不能超过50")
    @ApiModelProperty("备注")
    private String remark;
    /**
    * 是否删除
    */
    @NotNull(message="[是否删除]不能为空")
    @ApiModelProperty("是否删除")
    private Boolean isDeleted;



}
