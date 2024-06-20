//package com.shop.service;
//
//import com.shop.constant.ItemSellStatus;
//import com.shop.entity.Category;
//import com.shop.entity.Item;
//import com.shop.repository.CategoryRepository;
//import com.shop.repository.ItemRepository;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Log4j2
//class CategoryServiceTest {
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Test
//    @DisplayName("카테고리 저장 테스트")
//    void saveCategory() {
//        // 카테고리 생성 및 저장
//        Category category = new Category();
//        category.setCategoryName("음식");
//
//        Category savedCategory = categoryRepository.save(category);
//        categoryRepository.flush(); // 강제로 데이터베이스에 변경 사항 반영
//
//        log.info("savedCategory : " + savedCategory);
//
//        // 아이템 생성 및 카테고리 설정
//        Item item = new Item();
//        item.setItemNm("라면");
//        item.setPrice(1500);
//        item.setItemDetail("라면 상세 설명");
//        item.setItemSellStatus(ItemSellStatus.SELL);
//        item.setStockNumber(100);
//        item.setRegTime(LocalDateTime.now());
//        item.setUpdateTime(LocalDateTime.now());
//        item.setCategory(savedCategory);
//
//        Item savedItem = itemRepository.save(item);
//        itemRepository.flush(); // 강제로 데이터베이스에 변경 사항 반영
//
//        log.info("savedItem : " + savedItem);
//
//        // savedCategory 객체의 items 리스트에 저장된 item 추가
//        savedCategory.addItem(savedItem);
//        categoryRepository.save(savedCategory);
////        categoryRepository.flush(); // 강제로 데이터베이스에 변경 사항 반영
//
//        log.info("savedCategory after adding item: " + savedCategory);
//        log.info("savedCategory Items: " + savedCategory.getItems());
//    }
//}
