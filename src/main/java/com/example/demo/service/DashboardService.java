package com.example.demo.service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public DashboardDTO getDashboardData() {
        long prodCount = productRepository.count();
        long userCount = userRepository.count();

        List<Product> products = productRepository.findAll();
        double totalRevenue = 0;
        long totalSold = 0;

        for (Product product : products) {
            totalSold += product.getSold();
        }

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            totalRevenue += order.getTotalMoney();
        }

        return new DashboardDTO(prodCount, userCount, totalRevenue, totalSold);
    }
}
