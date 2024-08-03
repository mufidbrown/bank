package com.dbs.controller;

import com.dbs.config.BaseResponse;
import com.dbs.dto.AccountRequestDTO;
import com.dbs.dto.TransferRequestDTO;
import com.dbs.entity.Account;
import com.dbs.exception.AccountNotFoundException;
import com.dbs.service.AccountService;
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


//    @PostMapping("/create")
//    public ResponseEntity<BaseResponse<Account>> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
//        try {
//            Account account = accountService.createAccount(accountRequestDTO);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(BaseResponse.ok(HttpStatus.CREATED.value(), "Account created successfully", account));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), "Failed to create account: " + e.getMessage()));
//        }
//    }

//    @GetMapping("/{accountNumber}")
//    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
//        try {
//            Account account = accountService.getAccountByNumber(accountNumber);
//            return new ResponseEntity<>(account, HttpStatus.OK);
//        } catch (AccountNotFoundException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

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


//
//    // Endpoint untuk mendapatkan akun berdasarkan nomor rekening
//    @GetMapping("/{accountNumber}")
//    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
//        try {
//            Account account = accountService.getAccountByNumber(accountNumber);
//            return new ResponseEntity<>(account, HttpStatus.OK);
//        } catch (AccountNotFoundException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }


//
//    @PostMapping("/create")
//    public ResponseEntity<BaseResponse<Account>> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
//        try {
//            Account account = accountService.createAccount(accountRequestDTO);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(BaseResponse.ok(HttpStatus.CREATED.value(), "Account created successfully", account));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), "Failed to create account: " + e.getMessage()));
//        }
//    }


//    @GetMapping("/{accountNumber}")
//    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
//        Account account = accountService.getAccountByNumber(accountNumber);
//        if (account != null) {
//            return ResponseEntity.ok(account);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }



//    @PostMapping("/transfer")
//    public ResponseEntity<String> transferFunds(@RequestBody TransferRequestDTO transferRequestDTO) {
//        String fromAccountNumber = transferRequestDTO.getFromAccountNumber();
//        String toAccountNumber = transferRequestDTO.getToAccountNumber();
//        BigDecimal amount = transferRequestDTO.getAmount();
//
//        // Implementasi logika transfer dana
//        boolean success = accountService.transferFunds(fromAccountNumber, toAccountNumber, amount);
//
//        if (success) {
//            return ResponseEntity.ok("Transfer successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer failed");
//        }
//    }
}

