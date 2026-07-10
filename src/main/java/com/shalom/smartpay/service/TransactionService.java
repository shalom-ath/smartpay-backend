package com.shalom.smartpay.service;

import com.shalom.smartpay.dto.TransactionResponse;
import com.shalom.smartpay.dto.TransferRequest;
import com.shalom.smartpay.dto.TransferResponse;
import com.shalom.smartpay.entity.*;
import com.shalom.smartpay.exception.InsufficientBalanceException;
import com.shalom.smartpay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final RedisService redisService;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        User sender = userRepository.findByEmail(request.getSenderEmail())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = walletRepository.findByOwner(sender)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findByOwner(receiver)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        senderWallet.setBalance(
                senderWallet.getBalance().subtract(request.getAmount())
        );

        receiverWallet.setBalance(
                receiverWallet.getBalance().add(request.getAmount())
        );

        Wallet updatedSender = walletRepository.save(senderWallet);
        Wallet updatedReceiver = walletRepository.save(receiverWallet);

        redisService.evictBalance(sender.getEmail());
        redisService.evictBalance(receiver.getEmail());

        Transaction transaction = Transaction.builder()
                .senderWallet(senderWallet)
                .receiverWallet(receiverWallet)
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .build();

        transactionRepository.save(transaction);

        return TransferResponse.builder()
                .transactionId(transaction.getId())
                .senderBalance(senderWallet.getBalance())
                .receiverBalance(receiverWallet.getBalance())
                .message("Money transferred successfully")
                .build();
    }

    public List<TransactionResponse> getTransactionHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        List<Transaction> transactions =
                transactionRepository.findBySenderWalletOrReceiverWallet(
                        wallet,
                        wallet
                );

        return transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .senderEmail(transaction.getSenderWallet().getOwner().getEmail())
                        .receiverEmail(transaction.getReceiverWallet().getOwner().getEmail())
                        .amount(transaction.getAmount())
                        .status(transaction.getStatus().name())
                        .timestamp(transaction.getTimestamp())
                        .build())
                .toList();
    }

}