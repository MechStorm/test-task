package com.example.testtask.controller;

import com.example.testtask.Entity.Wallet;
import com.example.testtask.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class WalletController {

    WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallets")
    public List<Wallet> getWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/wallets/{id}")
    public Wallet getWalletByID(@PathVariable UUID id) {
        Wallet wallet = walletService.getWalletByID(id);
        return wallet;
    }

    @PostMapping("/wallet")
    public Wallet createWallet(@RequestBody Wallet wallet) {
        if(!wallet.getOperationType().toLowerCase().equals("deposit") && !wallet.getOperationType().toLowerCase().equals("withdraw")) {
            throw new RuntimeException("Incorrect operation");
        }
        Wallet walletNew = walletService.createWallet(wallet);
        return walletNew;
    }

    @PutMapping("/wallet/{id}")
    public Wallet updateWallet(@PathVariable UUID id, Wallet wallet) {
        Wallet updateWallet = walletService.updateWallet(id, wallet);
        return updateWallet;
    }

    @DeleteMapping("/wallet/{id}")
    public String deleteWallet(@PathVariable UUID id) {
        if (walletService.deleteWallet(id)) {
            return "Success deleting wallet with id " + id;
        }
        return "Wallet with id " + id + " wasn't found!";
    }
}
