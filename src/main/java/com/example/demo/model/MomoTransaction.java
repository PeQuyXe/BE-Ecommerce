package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "momo_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MomoTransaction {

    @Id
    private String orderId;

    private String requestId;
    private String transId;
    private String orderInfo;
    private int amount;
    private String payType;
    private String message;
    private int resultCode;
    private long responseTime;

    private String extraData;
    private boolean isValidSignature;
    private boolean isPaid;
}
