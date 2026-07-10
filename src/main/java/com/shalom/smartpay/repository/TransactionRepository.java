package com.shalom.smartpay.repository;

import com.shalom.smartpay.entity.Transaction;
import com.shalom.smartpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderWalletOrReceiverWallet(
            Wallet senderWallet,
            Wallet receiverWallet
    );
}