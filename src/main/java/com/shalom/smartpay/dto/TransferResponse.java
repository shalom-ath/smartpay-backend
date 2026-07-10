package com.shalom.smartpay.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private Long transactionId;

    private BigDecimal senderBalance;

    private BigDecimal receiverBalance;

    private String message;
}