package com.example.demo.controller;

import com.example.demo.model.Coupon;
import com.example.demo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Optional<Coupon> coupon = couponService.getCouponById(id);
        return coupon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponService.saveCoupon(coupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon couponDetails) {
        Optional<Coupon> coupon = couponService.getCouponById(id);
        if (coupon.isPresent()) {
            Coupon updatedCoupon = coupon.get();
            updatedCoupon.setCode(couponDetails.getCode());
            updatedCoupon.setThumb(couponDetails.getThumb());
            updatedCoupon.setTitle(couponDetails.getTitle());
            updatedCoupon.setValue(couponDetails.getValue());
            updatedCoupon.setMinAmount(couponDetails.getMinAmount());
            updatedCoupon.setExpired(couponDetails.getExpired());
            updatedCoupon.setQuantity(couponDetails.getQuantity());
            updatedCoupon.setStatus(couponDetails.getStatus());
            couponService.saveCoupon(updatedCoupon);
            return ResponseEntity.ok(updatedCoupon);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        if (couponService.getCouponById(id).isPresent()) {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
