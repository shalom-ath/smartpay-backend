package com.shalom.smartpay.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @Email
    private String senderEmail;

    @Email
    private String receiverEmail;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amount;
}