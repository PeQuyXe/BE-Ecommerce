package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.ProductVariant;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository; // Repository để lấy Product từ prodId

    private static final DecimalFormat df = new DecimalFormat("#,### VND");

    public String buildOrderConfirmationEmail(Order order) {
        StringBuilder emailContent = new StringBuilder();

        // Định dạng ngày tháng
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = order.getOrderDate().format(dateFormatter);

        emailContent.append("Chào ").append(order.getFullname()).append(",\n\n")
                .append("Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi.\n\n")
                .append("🛒 Thông tin đơn hàng:\n")
                .append("📌 Mã đơn hàng: ").append(order.getOrderCode()).append("\n")
                .append("📅 Ngày đặt: ").append(formattedDate).append("\n")
                .append("💰 Tổng tiền: ").append(df.format(order.getTotalMoney())).append("\n")
                .append("📦 Trạng thái: ").append(order.getOrderStatus().getId()).append("\n\n")
                .append("📍 Địa chỉ giao hàng:\n")
                .append(order.getFullname()).append(" - ").append(order.getPhone()).append("\n")
                .append(order.getAddress()).append("\n\n");
//                .append("🛍 Danh sách sản phẩm:\n");

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        if (orderItems.isEmpty()) {
            emailContent.append("🛍 Danh sách sản phẩm: Không có sản phẩm nào.\n");
        } else {
            emailContent.append("🛍 Danh sách sản phẩm:\n");

            for (OrderItem item : orderItems) {
                ProductVariant variant = item.getProductVariant();
                Product product = productRepository.findById(variant.getProdId()).orElse(null);
                String productName = (product != null) ? product.getTitle() : "Sản phẩm không xác định";

                emailContent.append("- ").append(productName)
                        .append(" (x").append(item.getQuantity()).append(") ")
                        .append("- ").append(df.format(item.getTotalMoney())).append("\n");
            }
        }


        emailContent.append("\nĐơn hàng của bạn đang được xử lý và sẽ sớm được giao.\n")
                .append("Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của chúng tôi!\n\n")
                .append("🌟Cửa hàng StoreTech 🌟");

        return emailContent.toString();
    }

    public void sendOrderConfirmationEmail(Order order) {
        String toEmail = order.getUser().getEmail();
        String subject = "Xác nhận đơn hàng #" + order.getOrderCode();
        String content = buildOrderConfirmationEmail(order);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, false);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}
