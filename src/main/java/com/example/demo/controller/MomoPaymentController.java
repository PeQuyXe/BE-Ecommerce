package com.example.demo.controller;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.repository.MomoTransactionRepository;
import com.example.demo.service.MomoService;
import com.example.demo.model.MomoTransaction;
import com.example.demo.util.MomoSignatureUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/momo")
public class MomoPaymentController {

    private final MomoService momoService;
    private final MomoTransactionRepository transactionRepository;

    public MomoPaymentController(MomoService momoService, MomoTransactionRepository transactionRepository) {
        this.momoService = momoService;
        this.transactionRepository = transactionRepository;
    }

    // Tạo yêu cầu thanh toán
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest req) throws Exception {
        String response = momoService.createPayment(req);
        return ResponseEntity.ok(response);
    }

    // Xử lý callback IPN từ MoMo
    @PostMapping("/ipn")
    public ResponseEntity<String> handleIPN(@RequestBody Map<String, Object> ipnData) {
        try {
            // ✅ Trích xuất các trường cần thiết từ IPN
            String accessKey = (String) ipnData.get("accessKey");
            String amount = (String) ipnData.get("amount");
            String orderId = (String) ipnData.get("orderId");
            String orderInfo = (String) ipnData.get("orderInfo");
            String orderType = (String) ipnData.get("orderType");
            String partnerCode = (String) ipnData.get("partnerCode");
            String payType = (String) ipnData.get("payType");
            String requestId = (String) ipnData.get("requestId");
            String responseTime = String.valueOf(ipnData.get("responseTime"));
            String resultCode = String.valueOf(ipnData.get("resultCode"));
            String message = (String) ipnData.get("message");
            String transId = String.valueOf(ipnData.get("transId"));
            String extraData = (String) ipnData.get("extraData");
            String signature = (String) ipnData.get("signature");

            // ✅ Tạo raw data để xác thực lại chữ ký
            Map<String, String> dataToSign = new LinkedHashMap<>();
            dataToSign.put("accessKey", accessKey);
            dataToSign.put("amount", amount);
            dataToSign.put("extraData", extraData);
            dataToSign.put("message", message);
            dataToSign.put("orderId", orderId);
            dataToSign.put("orderInfo", orderInfo);
            dataToSign.put("orderType", orderType);
            dataToSign.put("partnerCode", partnerCode);
            dataToSign.put("payType", payType);
            dataToSign.put("requestId", requestId);
            dataToSign.put("responseTime", responseTime);
            dataToSign.put("resultCode", resultCode);
            dataToSign.put("transId", transId);

            String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz"; // sandbox
            String generatedSignature = MomoSignatureUtil.generateSignature(dataToSign, secretKey);

            // Kiểm tra chữ ký
            if (!generatedSignature.equals(signature)) {
                return ResponseEntity.badRequest().body("Invalid signature");
            }

            // Nếu thanh toán thành công (resultCode == 0)
            if ("0".equals(resultCode)) {
                // 💾 Lưu thông tin giao dịch vào bảng momo_transactions
                MomoTransaction tx = MomoTransaction.builder()
                        .orderId(orderId)
                        .requestId(requestId)
                        .transId(transId)
                        .orderInfo(orderInfo)
                        .amount(Integer.parseInt(amount))
                        .payType(payType)
                        .message(message)
                        .resultCode(Integer.parseInt(resultCode))
                        .responseTime(Long.parseLong(responseTime))
                        .extraData(extraData)
                        .isValidSignature(generatedSignature.equals(signature))
                        .isPaid(true)
                        .build();

                transactionRepository.save(tx);
                System.out.println("✅ Thanh toán MoMo thành công cho đơn hàng: " + orderId);
            } else {
                // Nếu thanh toán không thành công, lưu lại thông tin không thành công
                MomoTransaction tx = MomoTransaction.builder()
                        .orderId(orderId)
                        .requestId(requestId)
                        .transId(transId)
                        .orderInfo(orderInfo)
                        .amount(Integer.parseInt(amount))
                        .payType(payType)
                        .message(message)
                        .resultCode(Integer.parseInt(resultCode))
                        .responseTime(Long.parseLong(responseTime))
                        .extraData(extraData)
                        .isValidSignature(generatedSignature.equals(signature))
                        .isPaid(false)
                        .build();

                transactionRepository.save(tx);
                System.out.println("❌ Thanh toán MoMo thất bại cho đơn hàng: " + orderId);
            }

            return ResponseEntity.ok("IPN OK");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("IPN Error: " + e.getMessage());
        }
    }
}
