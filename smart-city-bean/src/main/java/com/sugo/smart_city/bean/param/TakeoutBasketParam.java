package com.sugo.smart_city.bean.param;

import com.sugo.smart_city.common.valid.Groups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutBasketParam implements Serializable {

    /**
     * 商品id
     */
    @NotNull(message = "商品Id不能为空", groups = {Groups.Add.class, Groups.Query.class})
    private Integer goodsId;

    /**
     * 加购数量
     */
    @Min(value = 0, message = "加购数量最小为0", groups = Groups.Add.class)
    @NotNull(groups = Groups.Add.class)
    @Null(groups = Groups.Query.class)
    private Integer quantity;

    /**
     * skuId组
     */
    private String skuIdGroup;

}