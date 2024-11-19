package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.UpdateStatusRequest;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusService orderStatusService;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Lấy thông tin đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        OrderDTO orderDTO = orderService.getOrderByIdWithItems(id);
        return ResponseEntity.ok(orderDTO);
    }
    // Lấy tất cả trạng thái đơn hàng
    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        return ResponseEntity.ok(orderStatuses);
    }

    // Lấy danh sách đơn hàng theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable int userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Tạo mới đơn hàng
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    // Cập nhật đơn hàng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequest));
    }
    // Cập nhật trạng thái cho đơn hàng
    @PostMapping("/order/{id}/update-status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable int id,
            @RequestBody UpdateStatusRequest statusRequest) {

        // Kiểm tra đầu vào
        if (statusRequest.getStatusId() == null) {
            throw new IllegalArgumentException("Status ID must not be null");
        }

        // Gọi service để cập nhật trạng thái
        Order updatedOrder = orderService.updateOrderStatus(id, statusRequest.getStatusId());

        // Trả về thông tin đơn hàng đã được cập nhật
        return ResponseEntity.ok(updatedOrder);
    }


    // Xóa đơn hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
