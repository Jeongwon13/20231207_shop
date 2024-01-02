package com.hy.shop.item.model.dao;

import com.hy.shop.item.model.vo.Item;
import com.hy.shop.item.model.vo.ItemImage;
import com.hy.shop.item.model.vo.ItemType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    List<ItemType> selectOneItemType(int itemTypeCd);


    int insertItem(Map<String, Object> params);
    Long selectLastItemNo();

    int insertItemImageList(List<ItemImage> itemImageList);
}

