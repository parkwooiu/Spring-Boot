package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@ToString(exclude = "order") // toString에서 order 필드는 제외하고 출력
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")  // 외래키 설정
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 가격
    private int count; // 수량

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item); // 주문할 상품과 수량을 세팅함
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice()); // 상품 가격 -> 주문 상품

        item.removeStock(count); // 주문 수량 만큼 재고 수량을 감소

        return orderItem;
    }

    // 해당 주문 상품의 총 가격 계산
    public int getTotalPrice() {
        return orderPrice * count;
    }

    // 주문 취소 시 상품의 재고를 원상 복구
    public void cancel() {
        this.getItem().addStock(count);
    }
}
