package com.hy.shop.item.model.vo;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Alias("Item")
@Getter
public class Item {
    private Long itemNo;
    private String itemName;
    private int itemPrice;
    private int stockQuantity;
    private String itemContent;
    private int itemCategoryId;
    private int itemTypeCd;


    private Timestamp registrationDt;
    private Timestamp updatedDt;

    private String itemImage;
    private String itemImage2;
    private List<ItemImage> imageList;

    public void setImageList(List<ItemImage> imageList) {
        this.imageList = imageList;
    }
}