package com.hy.shop.item.model.service;

import com.hy.shop.commom.util.Util;
import com.hy.shop.item.model.dao.ItemMapper;
import com.hy.shop.item.model.vo.ItemCategory;
import com.hy.shop.item.model.vo.ItemImage;
import com.hy.shop.item.model.vo.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemMapper itemMapper;

    public List<ItemType> selectOneItemType(int itemTypeCd) {
        return itemMapper.selectOneItemType(itemTypeCd);
    }

    /**
     * 아이템 이미지 폴더에 파일 생성 및 경로 삽입
     * @param params
     * @param imageList
     * @param folderPath
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor = { Exception.class })
    public int insertItem(Map<String, Object> params, List<MultipartFile> imageList, String folderPath) throws IOException {

        String itemImageName = Util.fileRename(imageList.get(0).getOriginalFilename());
        String itemImageName2 = Util.fileRename(imageList.get(1).getOriginalFilename());

        log.info("itemImageName::::{} ", itemImageName);
        log.info("itemImageName2::::{} ", itemImageName2);

        String itemImage = "/images/items/" + itemImageName;
        String itemImage2 = "/images/items/" + itemImageName2;

        params.put("itemImage", itemImage);
        params.put("itemImage2", itemImage2);
        log.info("params:::: {} ", params);
        int itemId = itemMapper.insertItem(params);

        log.info("imageList::::{}", imageList);

        for (MultipartFile file : imageList) {
            log.info("File name: {}", file.getOriginalFilename());
            log.info("Content type: {}", file.getContentType());
            log.info("Size: {}", file.getSize());
        }

        if (itemId > 0) {
            List<ItemImage> itemImageList = new ArrayList<>();
            List<String> reNameList = new ArrayList<>();
            reNameList.add(itemImageName);
            reNameList.add(itemImageName2);
            log.info("reNameList::::@@@@@@@{}", reNameList);

            for (int i = 0; i < imageList.size(); i++) {
                if (imageList.get(i).getSize() > 0) {
                    String reName = Util.fileRename(imageList.get(i).getOriginalFilename());
                    reNameList.add(reName);


                    log.info("reName:::: {}", reName);
                    log.info("reNameList:::: {}", reNameList);
                    Long itemImgNo = itemMapper.selectLastItemNo();

                    log.info("itemImgNo:::: {}", itemImgNo);

                    ItemImage img = ItemImage.builder()
                            .itemNo(itemImgNo)
                            .imageLevel(i)
                            .imageOriginal(imageList.get(i).getOriginalFilename())
                            .imageReName(reName)
                            .build();


                    log.info("img::::{}", img.getImageReName());

                    itemImageList.add(img);
                    log.info("imageReName::::{}", img.getImageReName());


                }
            }
            if(!itemImageList.isEmpty()) {

                int result = itemMapper.insertItemImageList(itemImageList);

                if(result == itemImageList.size()) {

                    for(int i=0 ; i < itemImageList.size() ; i++) {
                        int index = itemImageList.get(i).getImageLevel();

                        imageList.get(index).transferTo(new File(folderPath + reNameList.get(i) ));
                    }
                }
            }
        }
        return itemId;
    }

    /**
     * 화면에 뿌리기 위한 아이템 List
     * @param itemTypeCd
     * @return
     */
    public List<Map<String, Object>> selectListItem(String itemTypeCd) {
        return itemMapper.selectListItem(itemTypeCd);
    }

    /**
     * CategoryId 가져오기
     * @param itemCategoryId
     * @return
     */
    public List<ItemCategory> selectOneItemCategoryId(int itemCategoryId) {
        return itemMapper.selectOneItemCategoryId(itemCategoryId);
    }
}




