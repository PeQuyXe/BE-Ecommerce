package com.example.demo.service;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public List<Cart> getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addCart(CartDTO cartDTO) {
        // Tạo mới đối tượng Cart
        Cart cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        cart.setCreateAt(LocalDateTime.now());
        cart.setUpdateAt(LocalDateTime.now());

        // Tính tổng giá tiền
        int totalPrice = cartDTO.getItems().stream()
                .mapToInt(item -> item.getQuantity() * item.getPrice())
                .sum();
        cart.setTotalPrice(totalPrice);

        Cart savedCart = cartRepository.save(cart);

        // Lưu các mục CartItem
        for (CartItemDTO itemDTO : cartDTO.getItems()) {
            CartItem item = new CartItem();
            item.setCart(savedCart);
            item.setProductVariantId(itemDTO.getProductVariantId());
            item.setQuantity(itemDTO.getQuantity());
            cartItemRepository.save(item);
        }

        return savedCart;
    }

    public Cart updateCart(Integer cartId, CartDTO cartDTO) {
        // Lấy thông tin Cart hiện tại
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Cập nhật thông tin Cart
        cart.setUpdateAt(LocalDateTime.now());

        // Tính tổng giá tiền
        int totalPrice = cartDTO.getItems().stream()
                .mapToInt(item -> item.getQuantity() * item.getPrice())
                .sum();
        cart.setTotalPrice(totalPrice);

        // Xóa các CartItem cũ
        cartItemRepository.deleteByCartId(cartId);

        // Lưu các CartItem mới
        for (CartItemDTO itemDTO : cartDTO.getItems()) {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductVariantId(itemDTO.getProductVariantId());
            item.setQuantity(itemDTO.getQuantity());
            cartItemRepository.save(item);
        }

        return cartRepository.save(cart);
    }

    public void deleteCart(Integer cartId) {
        cartItemRepository.deleteByCartId(cartId);
        cartRepository.deleteById(cartId);
    }

    public void clearCartByUserId(Integer userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        for (Cart cart : carts) {
            cartItemRepository.deleteByCartId(cart.getId());
            cartRepository.delete(cart);
        }
    }
}
