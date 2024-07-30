package com.dbs.controller;

import com.dbs.config.BaseResponse;
import com.dbs.dto.AccountRequestDTO;
import com.dbs.entity.Account;
import com.dbs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//    public ResponseEntity<?> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
//        Account account = accountService.createAccount(accountRequestDTO);
//        return ResponseEntity.ok(account);
//    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Account>> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            Account account = accountService.createAccount(accountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(BaseResponse.ok(HttpStatus.CREATED.value(), "Account created successfully", account));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), "Failed to create account: " + e.getMessage()));
        }
    }
}

