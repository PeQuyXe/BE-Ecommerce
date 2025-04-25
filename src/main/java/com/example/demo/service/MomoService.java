package com.example.demo.service;


import com.example.demo.dto.PaymentRequest;
import com.example.demo.util.MomoSignatureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.*;
import java.util.*;

@Service
public class MomoService {
    private static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String PARTNER_CODE = "Momo123456789"; // sandbox
    private static final String ACCESS_KEY = "F8BBA842ECF85";
    private static final String SECRET_KEY = "K951B6PE1waDMi640xX08PD3vg6EkVlz";

    public String createPayment(PaymentRequest req) throws Exception {
        String orderId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();

        Map<String, String> rawData = new LinkedHashMap<>();
        rawData.put("accessKey", ACCESS_KEY);
        rawData.put("amount", String.valueOf(req.getAmount()));
        rawData.put("extraData", "");
        rawData.put("ipnUrl", "https://your-server.com/api/payment/momo/ipn");
        rawData.put("orderId", orderId);
        rawData.put("orderInfo", "Thanh toán đơn hàng #" + orderId);
        rawData.put("partnerCode", PARTNER_CODE);
        rawData.put("redirectUrl", "https://your-client.com/success");
        rawData.put("requestId", requestId);
        rawData.put("requestType", "captureWallet");
        rawData.put("lang", "vi");

        String signature = MomoSignatureUtil.generateSignature(rawData, SECRET_KEY);
        rawData.put("signature", signature);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(rawData)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response.body(); // Trả JSON raw từ MoMo
    }
}
