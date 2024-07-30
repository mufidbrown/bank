package com.dbs.service;

import com.dbs.entity.Account;
import com.dbs.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount != null && toAccount != null && fromAccount.getBalance().compareTo(amount) >= 0) {
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            return true;
        }
        return false;
    }

}
