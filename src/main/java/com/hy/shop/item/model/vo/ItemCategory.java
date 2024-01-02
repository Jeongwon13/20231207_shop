package com.hy.shop.item.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Alias("ItemCategory")
@AllArgsConstructor
@Getter
public class ItemCategory {
    private int itemCategoryId;
    private String itemCategoryName;


}
