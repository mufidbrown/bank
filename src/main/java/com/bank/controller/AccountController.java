package com.bank.controller;

import com.bank.config.BaseResponse;
import com.bank.dto.AccountRequestDTO;
import com.bank.dto.TransferRequestDTO;
import com.bank.entity.Account;
import com.bank.exception.AccountNotFoundException;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

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


    @GetMapping("/{accountNumber}")
    public ResponseEntity<BaseResponse<Account>> getAccount(@PathVariable String accountNumber) {
        try {
            Account account = accountService.getAccountByNumber(accountNumber);
            return ResponseEntity.ok(BaseResponse.ok(HttpStatus.OK.value(), "Account retrieved successfully", account));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.error(HttpStatus.NOT_FOUND.value(), "Account not found: " + e.getMessage()));
        }
    }


    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {
            boolean success = accountService.transferFunds(
                    transferRequestDTO.getFromAccountNumber(),
                    transferRequestDTO.getToAccountNumber(),
                    transferRequestDTO.getAmount()
            );
            if (success) {
                return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Transfer failed", HttpStatus.BAD_REQUEST);
            }
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Insufficient funds or invalid data", HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Account balance is missing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

