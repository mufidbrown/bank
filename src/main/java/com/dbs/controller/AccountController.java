package com.dbs.controller;

import com.dbs.entity.Account;
import com.dbs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

//    @Autowired
//    private UserService userService;

        @Autowired
        private AccountService accountService;

        @GetMapping("/{accountNumber}")
        public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
            Account account = accountService.getAccountByNumber(accountNumber);
            if (account != null) {
                return ResponseEntity.ok(account);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestParam String fromAccountNumber,
                                                @RequestParam String toAccountNumber,
                                                @RequestParam BigDecimal amount) {
        boolean success = accountService.transferFunds(fromAccountNumber, toAccountNumber, amount);
        if (success) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.badRequest().body("Transfer failed");
        }
    }
//
//    @GetMapping("/profile")
//    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.getUserByUsername(userDetails.getUsername());
//        return ResponseEntity.ok(user);
//    }
}
