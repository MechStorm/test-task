package com.example.testtask.service;


import com.example.testtask.Entity.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    List<Wallet> getAllWallets();
    Wallet getWalletByID(UUID id);
    Wallet createWallet(Wallet wallet);
    Wallet updateWallet(UUID id, Wallet wallet);
    boolean deleteWallet(UUID id);
}
