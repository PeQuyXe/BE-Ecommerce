package com.example.demo.controller;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.model.Product;
import com.example.demo.service.DashboardService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardData() {
        DashboardDTO dashboard = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/product/top-sold")
    public ResponseEntity<List<Product>> getTopSoldProducts() {
        List<Product> products = productService.getTopSoldProducts();
        return ResponseEntity.ok(products);
    }
    // Lấy sản phẩm được xem nhiều nhất
    @GetMapping("/top-viewed")
    public ResponseEntity<List<Product>> getTopViewedProducts() {
        List<Product> products = productService.getTopViewedProducts();
        return ResponseEntity.ok(products);
    }

    // API để lấy tổng doanh thu trong khoảng thời gian
    @GetMapping("/total-revenue")
    public Double getTotalRevenue(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay(); // Start of the day (00:00:00)
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59); // End of the day (23:59:59)

        return orderService.getTotalRevenue(start, end);
    }

    // API để lấy tổng doanh thu từ tất cả đơn hàng
    @GetMapping("/total-revenue-all")
    public Double getTotalRevenue() {
        return orderService.getTotalRevenue();
    }
}
