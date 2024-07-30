package com.dbs.service;

import com.dbs.dto.AccountRequestDTO;
import com.dbs.entity.Account;
import com.dbs.entity.User;
import com.dbs.repository.AccountRepository;
import com.dbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    public Account createAccount(AccountRequestDTO accountRequestDTO) {
        User user = userRepository.findById(accountRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountHolderName(accountRequestDTO.getAccountHolderName());
        account.setUser(user);

        return accountRepository.save(account);
    }


    private String generateAccountNumber() {
        // Implementasi untuk membuat nomor akun yang unik, misalnya dengan UUID
        return UUID.randomUUID().toString();
    }

    //    public Account createAccount(String accountHolderName) {
//        Account account = new Account();
//        account.setAccountHolderName(accountHolderName);
//        account.setAccountNumber(generateAccountNumber());
//        return accountRepository.save(account);
//    }


//    private String generateAccountNumber() {
//        // Contoh sederhana: nomor rekening 10 digit
//        Random random = new Random();
//        StringBuilder accountNumber = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            accountNumber.append(random.nextInt(10));
//        }
//        return accountNumber.toString();
//    }

}
