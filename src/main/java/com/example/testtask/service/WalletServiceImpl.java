package com.example.testtask.service;

import com.example.testtask.Entity.Wallet;
import com.example.testtask.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet getWalletByID(UUID id) {
        return walletRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The Wallet wasn't found!"));
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        if (walletRepository.existsById(wallet.getId())) {
            Wallet walletCurrent = walletRepository.findById(wallet.getId()).orElseThrow(() -> new EntityNotFoundException("The Wallet wasn't found!"));
            if (wallet.getOperationType().toLowerCase().equals("deposit")) {
                walletCurrent.setAmount(walletCurrent.getAmount() + wallet.getAmount());
            } else if (wallet.getOperationType().toLowerCase().equals("withdraw")) {
                if ((walletCurrent.getAmount() - wallet.getAmount()) < 0) {
                    throw new RuntimeException("Negative balance. You do not have enough money");
                } else {
                    walletCurrent.setAmount(walletCurrent.getAmount() - wallet.getAmount());
                }
            }
            return walletRepository.saveAndFlush(walletCurrent);
        }
        return walletRepository.saveAndFlush(wallet);
    }

    @Override
    public Wallet updateWallet(UUID id, Wallet wallet) {
        walletRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The Wallet wasn't found!"));

        return walletRepository.saveAndFlush(wallet);
    }

    @Override
    public boolean deleteWallet(UUID id) {
        walletRepository.deleteById(id);
        boolean checkWallet = walletRepository.existsById(id);
        return checkWallet;
    }
}
