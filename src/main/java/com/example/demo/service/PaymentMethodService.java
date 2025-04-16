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

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod) {
        PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment Method not found"));
        existingPaymentMethod.setName(paymentMethod.getName());
        existingPaymentMethod.setDisplayName(paymentMethod.getDisplayName());
        existingPaymentMethod.setThumb(paymentMethod.getThumb());
        existingPaymentMethod.setStatus(paymentMethod.getStatus());
        return paymentMethodRepository.save(existingPaymentMethod);
    }

    public void deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment Method not found"));
        paymentMethodRepository.delete(paymentMethod);
    }


    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found with ID: " + id));
    }
}
