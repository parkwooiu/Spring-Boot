package com.shop.service;

import com.shop.constant.Category;
import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;

import com.shop.entity.Item;
import com.shop.entity.ItemImg;
//import com.shop.entity.Category;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
//import com.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
//    private final CategoryRepository categoryRepository;

    // 상품 저장
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 엔티티 생성
        Item item = itemFormDto.createItem();

//        // 카테고리 설정
//        if (itemFormDto.getCategoryName() != null) {
//            Category category = categoryRepository.findByCategoryName(itemFormDto.getCategoryName())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid category name: " + itemFormDto.getCategoryName()));
//            item.setCategory(category);
//        }

        // 상품 저장
        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if (i == 0) {
                itemImg.setRepimgYn("Y");  // 첫번째 사진이 대표 이미지
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    // 상품 상세 정보 조회
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        // 해당 아이템의 이미지 리스트 조회
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // ItemImg -> ItemImgDto 변환
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.ItemImgOfItemImgDto(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        // Item -> ItemFormDto 변환
        Optional<Item> result = itemRepository.findById(itemId);
        Item item = result.orElseThrow(() -> new EntityNotFoundException());

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    // 상품 수정
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 해당 아이템 조회
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        // 아이템 정보 업데이트
        item.setItemNm(itemFormDto.getItemNm());
        item.setPrice(itemFormDto.getPrice());
        item.setStockNumber(itemFormDto.getStockNumber());
        item.setItemDetail(itemFormDto.getItemDetail());
        item.setItemSellStatus(itemFormDto.getItemSellStatus());

//        // 카테고리 설정
//        if (itemFormDto.getCategoryName() != null) {
//            Category category = categoryRepository.findByCategoryName(itemFormDto.getCategoryName())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid category name: " + itemFormDto.getCategoryName()));
//            item.setCategory(category);
//        } else {
//            item.setCategory(null); // 카테고리가 없는 경우 null로 설정
//        }

        // 아이템 저장
        itemRepository.save(item);

        // 이미지 업데이트
        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        for (int i = 0; i < itemImgIds.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    // 검색 조건 & 페이징 처리
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdiminItemPage(itemSearchDto, pageable);
    }

    // 메인 페이지
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }


        public List<Item> getItemsByCategory(Category category) {
        return itemRepository.findByCategory(category);
    }

//    // 모든 카테고리 조회
//    @Transactional(readOnly = true)
//    public List<CategoryDto> getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        List<CategoryDto> categoryDtos = new ArrayList<>();
//        for (Category category : categories) {
//            CategoryDto categoryDto = new CategoryDto();
//            categoryDto.setId(category.getId());
//            categoryDto.setCategoryName(category.getCategoryName());
//            categoryDtos.add(categoryDto);
//        }
//        return categoryDtos;
//    }
}
