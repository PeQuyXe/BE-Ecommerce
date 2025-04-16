package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    // Tính tổng doanh thu trong khoảng thời gian (startDate, endDate)
    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Double findTotalRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Tính tổng doanh thu trong tất cả các đơn hàng
    @Query("SELECT SUM(o.totalMoney) FROM Order o")
    Double findTotalRevenue();

}
