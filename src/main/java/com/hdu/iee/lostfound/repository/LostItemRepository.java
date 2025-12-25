package com.hdu.iee.lostfound.repository;

import com.hdu.iee.lostfound.entity.LostItem;
import com.hdu.iee.lostfound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {

    @Query("SELECT li FROM LostItem li WHERE (:keyword IS NULL OR LOWER(li.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(li.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(li.location) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<LostItem> searchByKeyword(@Param("keyword") String keyword);

    List<LostItem> findByPublisherOrderByCreatedAtDesc(User publisher);
}

