package com.shalom.smartpay.controller;


import com.shalom.smartpay.dto.CreateWalletResponse;
import com.shalom.smartpay.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.shalom.smartpay.dto.AddMoneyRequest;
import com.shalom.smartpay.dto.WalletResponse;


@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/{email}")
    public CreateWalletResponse createWallet(@PathVariable String email) {

        return walletService.createWallet(email);
    }

    @PostMapping("/add-money")
    public WalletResponse addMoney(
            @RequestBody AddMoneyRequest request) {

        return walletService.addMoney(request);

    }

    @GetMapping("/balance/{email}")
    public WalletResponse getBalance(@PathVariable String email) {
        return walletService.getBalance(email);
    }

}
