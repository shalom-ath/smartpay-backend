package com.shalom.smartpay.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;
}
