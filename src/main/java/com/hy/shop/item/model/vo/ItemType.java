package com.hy.shop.item.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Alias("ItemType")
@Getter
@AllArgsConstructor
public class ItemType {
    private int itemCode;
    private String itemName;

}
