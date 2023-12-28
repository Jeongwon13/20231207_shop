package com.hy.shop.item.model.service;

import com.hy.shop.item.model.dao.ItemMapper;
import com.hy.shop.item.model.vo.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemMapper itemMapper;

    public List<ItemType> selectItemType() {
        List<ItemType> itemTypeList = itemMapper.selectItemType();
        log.info("itemTypeList:::: {} ",itemTypeList);
        return itemTypeList;
    }
}
