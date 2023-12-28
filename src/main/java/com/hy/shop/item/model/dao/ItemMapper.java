package com.hy.shop.item.model.dao;

import com.hy.shop.item.model.vo.ItemType;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<ItemType> selectItemType();



}

