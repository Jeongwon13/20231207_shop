package com.hy.shop.item.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Alias("ItemImage")
@AllArgsConstructor
@Builder
@Getter
public class ItemImage {

    private Long imageNo;
    private String imageReName;
    private String imageOriginal;
    private int imageLevel;
    private Timestamp registrationDt;
    private Long itemNo;


}