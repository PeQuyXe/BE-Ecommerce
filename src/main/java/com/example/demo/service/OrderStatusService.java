package com.example.demo.service;

import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusService {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    // Lấy tất cả trạng thái đơn hàng
    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    // Lấy trạng thái đơn hàng theo ID
    public Optional<OrderStatus> getOrderStatusById(int id) {
        return orderStatusRepository.findById(id);
    }

    // Thêm trạng thái mới
    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    // Cập nhật trạng thái đơn hàng
    public OrderStatus updateOrderStatus(int id, OrderStatus orderStatus) {
        if (orderStatusRepository.existsById(id)) {
            orderStatus.setId(id);
            return orderStatusRepository.save(orderStatus);
        }
        return null;
    }

    // Xóa trạng thái đơn hàng
    public boolean deleteOrderStatus(int id) {
        if (orderStatusRepository.existsById(id)) {
            orderStatusRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
