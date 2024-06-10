package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem qItem= QItem.item;

        // select * from 테이블명 where 조건
        List<Item> item = queryFactory
                .selectFrom(qItem)
                .where(qItem.itemNm.eq("한라산"))
                .fetch();

        item.forEach(list->log.info(list));


        // Item item = queryFactory.select()
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QItem qItem= QItem.item;

        // select * from 테이블명 where 조건
        Item item = queryFactory
                .selectFrom(qItem)
                .where( qItem.itemNm.eq("한라산").and(qItem.itemDetail.eq("상세1")) )
                .fetchOne();

        log.info(item);

    }
    @Test
    @DisplayName("Querydsl 조회 테스트3")
    public void queryDslTest4() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        List<Item> item = queryFactory
                .selectFrom(qItem)
                .where(qItem.price.goe(20000))
                .fetch();
        log.info(item);
    }
    @Test
    @DisplayName("상품 조회2")
    public void queryDslTest5() {
        //JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        //createItemList2();

        BooleanBuilder builder = new BooleanBuilder();

        QItem item = QItem.item;

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        builder.and(item.itemDetail.like("%"+itemDetail+"%"));
        builder.and(item.price.eq(price));
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0,5);
        Page<Item> itemPageResult = itemRepository.findAll(builder, pageable);

        log.info("---------------------------");
        log.info(itemPageResult);
        log.info("---------------------------");

        log.info("total elements : " + itemPageResult.getTotalElements());
        log.info("total pages : " + itemPageResult.getTotalPages());
    }



    @Test
    @Disabled("상품 저장 테스트")
    public void createItemtest(){
        Item item = new Item();

        item.setItemNm("라면1");
        item.setPrice(1000);
        item.setItemDetail("라면 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStoackNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());


        Item savedItem = itemRepository.save(item);
        log.info(savedItem);
    }

    @Test
    @DisplayName("라면 검색")
    public void findByNameTest() {
        Item item = itemRepository.findByItemNm("라면1");
        log.info("라면" + item);
    }
    @Test
    @DisplayName("라면 상세 설명")
    public void findByItemDetailTest() {
        List<Item> item = itemRepository.findByItemDetail("상품");
        item.forEach(list->log.info(list));
    }
    @Test
    @DisplayName("라면 & 라면 상세 설명")
    public void findByItemAndItemDetailTest() {
        List<Item> item = itemRepository.findByItemNmAndItemDetail("라면","라면 상세 설명");
        item.forEach(list->log.info(list));

    }
    @Test
    @DisplayName("price가 30000이하 검색")
    public void findByPriceTest() {
        List<Item> list = itemRepository.findByPriceLessThan(30000);

        list.forEach(result->log.info(result));
    }
    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemtest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(100005);
        for (Item item : itemList) {
            log.info(item);
        }
    }
    @Test
    @DisplayName("findbyItemDetailByNative ")
    public void findbyItemDetailByNative() {
        List<Item> items = itemRepository.findByItemDetailByNative("라면");
        items.forEach(result->log.info(result));
    }
    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStoackNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStoackNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }
}