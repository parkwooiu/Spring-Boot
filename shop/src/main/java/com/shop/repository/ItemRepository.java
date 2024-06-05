package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> , QuerydslPredicateExecutor<Item> {

    Item findByItemNm(String itemNm);
   // Item findByItemDetail(String str);
  //  Item findByItemNmAndItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(int price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<Item> findByPriceGreaterThan(Integer price);


    @Query("select i from Item i " +
            "where i.itemDetail " +
            "like %:itemDetail% " +
            "order by  i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String detail);

    @Query("select i from Item i where i.itemNm like  %:itemNm% and i.itemDetail like %:itemDetail%")
    List<Item> findByItemNmAndItemDetail(@Param("itemNm") String item,@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item where item_detail like %:itemDetail% order by price asc", nativeQuery = true)
    List<Item> findbyItemDetailByNative(@Param("itemDetail") String itemDetail);
}
