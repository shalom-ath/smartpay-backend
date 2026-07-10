package com.shalom.smartpay.repository;

import com.shalom.smartpay.entity.User;
import com.shalom.smartpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByOwner(User owner);

}