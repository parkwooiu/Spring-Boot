package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public Long saveItem(ItemFormDto itemFormDto,
               List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    public ItemFormDto getItemDtl(Long itemid){
       List<ItemImg> itemImgList =
               itemImgRepository.findByItemIdOrderByIdDesc(itemid);
       List<ItemImgDto> itemImgDtoList = new ArrayList<>();

       //ItemImg(상품이미지) -> ItemImgDto 전달
       for (ItemImg itemImg : itemImgList) {
           ItemImgDto itemImgDto = ItemImgDto.ItemImgofItemImgDto(itemImg);
           itemImgDtoList.add(itemImgDto);
       }

       //Item(상품정보) ->itemFormDto 전달
      Optional<Item> result = itemRepository.findById(itemid);
       Item item = result.orElseThrow(() -> new EntityNotFoundException());

       ItemFormDto itemFormDto = ItemFormDto.of(item);
       itemFormDto.setItemImgDtoList(itemImgDtoList);

       return itemFormDto;
    }


}
