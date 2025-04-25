package com.example.demo.repository;

import com.example.demo.model.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
    List<SearchLog> findTop5ByOrderByTimestampDesc();
    @Query("SELECT s.prodId, COUNT(s) as cnt FROM SearchLog s GROUP BY s.prodId ORDER BY cnt DESC")
    List<Object[]> findPopularProducts();
}
