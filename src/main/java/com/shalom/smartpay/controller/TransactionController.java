package com.shalom.smartpay.controller;

import com.shalom.smartpay.dto.TransactionResponse;
import com.shalom.smartpay.dto.TransferRequest;
import com.shalom.smartpay.dto.TransferResponse;
import com.shalom.smartpay.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransferResponse transfer(
            @Valid @RequestBody TransferRequest request) {

        return transactionService.transfer(request);
    }

    @GetMapping("/history/{email}")
    public List<TransactionResponse> history(
            @PathVariable String email) {

        return transactionService.getTransactionHistory(email);

    }


}