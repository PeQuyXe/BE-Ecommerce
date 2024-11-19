package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderStatusDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private VariantsValueRepository variantsValueRepository;

    public Double getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findTotalRevenue(startDate, endDate);
    }

    // Tính tổng doanh thu từ tất cả đơn hàng
    public Double getTotalRevenue() {
        return orderRepository.findTotalRevenue();
    }
    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public OrderDTO getOrderByIdWithItems(int id) {
        // Lấy đơn hàng theo id
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        // Chuyển đổi sang DTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderCode(order.getOrderCode());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setFullname(order.getFullname());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setNote(order.getNote());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalMoney(order.getTotalMoney());
        orderDTO.setCouponId(order.getCouponId());

        // Map danh sách OrderItems
        List<OrderItemDTO> orderItems = order.getOrderItems().stream()
                .map(item -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    Product product = productRepository.findById(item.getProductVariant().getProdId())
                            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + item.getProductVariant().getProdId()));

                    itemDTO.setId(item.getId());
                    itemDTO.setProductName(product.getTitle()); // Tên sản phẩm
                    itemDTO.setProductImage(product.getThumb()); // Ảnh sản phẩm
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getPrice());
                    itemDTO.setTotalMoney(item.getTotalMoney());

                    // Lấy variantValues từ VariantsValue
                    if (item.getProductVariant() != null) {
                        Map<String, String> variantValues = new HashMap<>();
                        List<VariantsValue> variantsValues = variantsValueRepository.findByProductVariant(item.getProductVariant());

                        for (VariantsValue variantsValue : variantsValues) {
                            variantValues.put(
                                    variantsValue.getAttribute().getDisplayName(),
                                    variantsValue.getAttributeValue().getValueName()
                            );
                        }
                        itemDTO.setVariantValues(variantValues);
                    }

                    return itemDTO;
                })
                .collect(Collectors.toList());

        orderDTO.setOrderItems(orderItems);

        // Map thông tin trạng thái đơn hàng
        OrderStatus orderStatus = order.getOrderStatus();
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(orderStatus.getId());
        orderStatusDTO.setName(orderStatus.getName());
        orderStatusDTO.setDescription(orderStatus.getDescription());

        orderDTO.setOrderStatus(orderStatusDTO); // Gán thông tin trạng thái đơn hàng

        return orderDTO;
    }



    //     Lấy thông tin đơn hàng theo ID
    public Order getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }
    // Lấy tất cả đơn hàng của người dùng theo userId
    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }
    // Tạo đơn hàng mới
    public Order createOrder(OrderRequest orderRequest) {
        // Kiểm tra và lấy OrderStatus
        OrderStatus orderStatus = orderStatusRepository.findById(orderRequest.getOrderStatus().getId())
                .orElseThrow(() -> new RuntimeException("OrderStatus not found"));

        // Tạo đối tượng Order
        Order order = new Order();
        order.setOrderCode(orderRequest.getOrderCode());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setAddress(orderRequest.getAddress());
        order.setFullname(orderRequest.getFullname());
        order.setPhone(orderRequest.getPhone());
        order.setNote(orderRequest.getNote());
        order.setTotalMoney(orderRequest.getTotalMoney());
        order.setUserId(orderRequest.getUserId());
        order.setCouponId(orderRequest.getCouponId());
        order.setOrderStatus(orderStatus);

        // Lưu Order
        Order savedOrder = orderRepository.save(order);

        // Tạo và lưu các OrderItems
        for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            // Lấy danh sách ProductVariant theo prodId
            List<ProductVariant> productVariants = productVariantRepository.findByProdId(itemRequest.getProductId());

            // Kiểm tra xem có tồn tại ProductVariant không
            if (productVariants.isEmpty()) {
                throw new RuntimeException("ProductVariant not found for productId: " + itemRequest.getProductId());
            }

            // Lấy ProductVariant đầu tiên (Giả sử là duy nhất)
            ProductVariant productVariant = productVariants.get(0);

            // Tạo OrderItem và lưu
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProductVariant(productVariant);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setTotalMoney(itemRequest.getTotalMoney());

            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }
    public Order updateOrderStatus(int orderId, int statusId) {
        // Tìm kiếm đơn hàng
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Tìm kiếm trạng thái đơn hàng
        OrderStatus status = orderStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("OrderStatus not found with ID: " + statusId));

        // Cập nhật trạng thái cho đơn hàng
        order.setOrderStatus(status);

        // Lưu lại đơn hàng sau khi cập nhật
        return orderRepository.save(order);
    }

    // Cập nhật thông tin đơn hàng theo ID
    public Order updateOrder(int id, OrderRequest orderRequest) {
        // Kiểm tra đơn hàng tồn tại
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        // Kiểm tra và lấy OrderStatus
        OrderStatus orderStatus = orderStatusRepository.findById(orderRequest.getOrderStatus().getId())
                .orElseThrow(() -> new RuntimeException("OrderStatus not found"));

        // Cập nhật thông tin đơn hàng
        existingOrder.setFullname(orderRequest.getFullname());
        existingOrder.setPhone(orderRequest.getPhone());
        existingOrder.setAddress(orderRequest.getAddress());
        existingOrder.setNote(orderRequest.getNote());
        existingOrder.setOrderStatus(orderStatus);
        existingOrder.setTotalMoney(orderRequest.getTotalMoney());
        existingOrder.setCouponId(orderRequest.getCouponId());
        existingOrder.setOrderDate(orderRequest.getOrderDate());

        // Xóa các OrderItems cũ
        orderItemRepository.deleteByOrderId(existingOrder.getId());

        // Tạo và lưu các OrderItems mới
        for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            // Lấy danh sách ProductVariant theo prodId
            List<ProductVariant> productVariants = productVariantRepository.findByProdId(itemRequest.getProductId());

            // Kiểm tra xem có tồn tại ProductVariant không
            if (productVariants.isEmpty()) {
                throw new RuntimeException("ProductVariant not found for productId: " + itemRequest.getProductId());
            }

            // Lấy ProductVariant đầu tiên (Giả sử là duy nhất)
            ProductVariant productVariant = productVariants.get(0);

            // Tạo OrderItem và lưu
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(existingOrder);
            orderItem.setProductVariant(productVariant);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setTotalMoney(itemRequest.getTotalMoney());

            orderItemRepository.save(orderItem);
        }

        // Lưu lại thông tin đơn hàng đã cập nhật
        return orderRepository.save(existingOrder); // Đây là phương thức save của OrderRepository, không phải saveOrder
    }


    // Xóa đơn hàng theo ID
    @Transactional
    public void deleteOrder(int id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        orderItemRepository.deleteByOrderId(id);
        orderRepository.delete(existingOrder);
    }
}
