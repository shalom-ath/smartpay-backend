package com.shalom.smartpay.service;


import com.shalom.smartpay.dto.AddMoneyRequest;
import com.shalom.smartpay.dto.CreateWalletResponse;
import com.shalom.smartpay.dto.WalletResponse;
import com.shalom.smartpay.entity.User;
import com.shalom.smartpay.entity.Wallet;
import com.shalom.smartpay.service.RedisService;
import com.shalom.smartpay.repository.UserRepository;
import com.shalom.smartpay.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    private final RedisService redisService;

    public CreateWalletResponse createWallet(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(walletRepository.findByOwner(user).isPresent()) {

            throw new RuntimeException("Wallet already exists");
        }

        Wallet wallet = Wallet.builder()
                .owner(user)
                .build();

        walletRepository.save(wallet);

        return CreateWalletResponse.builder()
                .walletId(wallet.getId())
                .balance(wallet.getBalance())
                .message("Wallet created successfully")
                .build();
    }

    public WalletResponse addMoney(AddMoneyRequest request){

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByOwner(user).orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));

        walletRepository.save(wallet);

        redisService.evictBalance(request.getEmail());

        return WalletResponse.builder()
                .balance(wallet.getBalance())
                .message("Money added successfully")
                .build();
    }

    public WalletResponse getBalance(String email){

        BigDecimal cached = redisService.getCachedBalance(email);

        if(cached != null){

            return WalletResponse.builder()
                    .balance(cached)
                    .message("Balance fetched from Redis Cache")
                    .build();
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new  RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByOwner(user).orElseThrow(() -> new RuntimeException("Wallet not found"));

        redisService.cacheBalance(email,wallet.getBalance());

        return WalletResponse.builder()
                .balance(wallet.getBalance())
                .message("Balance fetched from Database")
                .build();


    }
}
