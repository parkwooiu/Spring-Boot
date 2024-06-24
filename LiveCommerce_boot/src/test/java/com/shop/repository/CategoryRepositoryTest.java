package com.shop.repository;

import com.shop.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        // 테스트 실행 전 초기화 작업 수행
        // 예시로 초기 카테고리 데이터를 데이터베이스에 저장하는 코드 작성
        Category category1 = new Category();
        category1.setName("TOPS");

        Category category2 = new Category();
        category2.setName("BOTTOMS");

        // 나머지 카테고리 객체 생성 및 저장

        em.persist(category1);
        em.persist(category2);
        // 나머지 카테고리 객체도 저장

        em.flush();
        em.clear();
    }


}
