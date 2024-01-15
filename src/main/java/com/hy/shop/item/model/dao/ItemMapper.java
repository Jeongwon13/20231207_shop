package com.hy.shop.item.model.dao;

import com.hy.shop.item.model.vo.ItemCategory;
import com.hy.shop.item.model.vo.ItemImage;
import com.hy.shop.item.model.vo.ItemType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    List<ItemType> selectOneItemType(int itemTypeCd);
    List<ItemCategory> selectOneItemCategoryId(int itemCategoryId);

    int insertItem(Map<String, Object> params);
    Long selectLastItemNo();

    int insertItemImageList(List<ItemImage> itemImageList);
    List<Map<String, Object>> selectListItem(String itemTypeCd);

}

