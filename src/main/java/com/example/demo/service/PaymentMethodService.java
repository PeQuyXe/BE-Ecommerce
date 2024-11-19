package com.example.demo.service;

import com.example.demo.model.PaymentMethod;
import com.example.demo.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // Get all payment methods
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    // Create a new payment method
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    // Update an existing payment method
    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod) {
        PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment Method not found"));
        existingPaymentMethod.setName(paymentMethod.getName());
        existingPaymentMethod.setDisplayName(paymentMethod.getDisplayName());
        existingPaymentMethod.setThumb(paymentMethod.getThumb());
        existingPaymentMethod.setStatus(paymentMethod.getStatus());
        return paymentMethodRepository.save(existingPaymentMethod);
    }

    // Delete a payment method
    public void deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment Method not found"));
        paymentMethodRepository.delete(paymentMethod);
    }


    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found with ID: " + id));
    }
}
