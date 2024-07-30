package com.dbs.controller;

import com.dbs.dto.AccountRequestDTO;
import com.dbs.entity.Account;
import com.dbs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

//    @PostMapping("/create")
//    public Account createAccount(@RequestParam String accountHolderName) {
//        return accountService.createAccount(accountHolderName);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        Account account = accountService.createAccount(accountRequestDTO);
        return ResponseEntity.ok(account);
    }

}

