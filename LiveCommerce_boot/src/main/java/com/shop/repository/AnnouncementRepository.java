package com.shop.repository;

import com.shop.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // JPA Repository를 상속받아 기본적인 CRUD 메서드를 제공
}