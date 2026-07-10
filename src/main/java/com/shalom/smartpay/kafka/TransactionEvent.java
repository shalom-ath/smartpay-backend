package com.shalom.smartpay.kafka;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEvent {

    private String senderEmail;

    private String receiverEmail;

    private BigDecimal amount;
}
