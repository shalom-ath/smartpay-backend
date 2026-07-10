package com.shalom.smartpay.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWalletResponse {

    private Long walletId;

    private BigDecimal balance;

    private String message;
}
