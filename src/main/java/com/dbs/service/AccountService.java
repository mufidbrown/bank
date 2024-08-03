package com.dbs.service;

import com.dbs.dto.AccountRequestDTO;
import com.dbs.entity.Account;
import com.dbs.entity.User;
import com.dbs.exception.AccountNotFoundException;
import com.dbs.repository.AccountRepository;
import com.dbs.repository.TransactionLogRepository;
import com.dbs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountService {


    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account createAccount(AccountRequestDTO accountRequestDTO) {
        User user = userRepository.findById(accountRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (accountRequestDTO.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber()); // Generate account number
        account.setAccountHolderName(accountRequestDTO.getAccountHolderName());
        account.setBalance(accountRequestDTO.getInitialBalance()); // Set initial balance
        account.setUser(user);

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
        Random random = new Random();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            accountNumber.append(random.nextInt(10)); // Generate random digit (0-9)
        }
        return accountNumber.toString();
    }


//    public Account createAccount(String accountHolderName, BigDecimal initialBalance, Long userId) {
//        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("Initial balance cannot be negative");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Account account = new Account();
//        account.setAccountNumber(generateAccountNumber());
//        account.setAccountHolderName(accountHolderName);
//        account.setBalance(initialBalance); // Set initial balance
//        account.setUser(user);
//
//        logger.info("Creating account: {}", account);
//
//        return accountRepository.save(account);
//    }
//
//    private String generateAccountNumber() {
//        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
//        Random random = new Random();
//        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
//            accountNumber.append(random.nextInt(10)); // Generate random digit (0-9)
//        }
//        return accountNumber.toString();
//    }
//
//    public Account getAccountByNumber(String accountNumber) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new AccountNotFoundException("Account not found");
//        }
//        return account;
//    }

    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException("One or both accounts not found");
        }

        if (fromAccount.getBalance() == null || toAccount.getBalance() == null) {
            throw new IllegalStateException("Account balance is missing");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log the transaction
        transactionService.logTransaction(fromAccountNumber, toAccountNumber, amount);

        logger.info("Transferred {} from account {} to account {}", amount, fromAccountNumber, toAccountNumber);

        return true;
    }
}



//
//    // Method untuk membuat akun baru dengan saldo
//    public Account createAccount(String accountHolderName, BigDecimal initialBalance) {
//        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("Initial balance cannot be negative");
//        }
//
//        String accountNumber = generateAccountNumber(); // Menghasilkan nomor rekening baru
//        Account account = new Account();
//        account.setAccountNumber(accountNumber);
//        account.setAccountHolderName(accountHolderName);
//        account.setBalance(initialBalance); // Atur saldo awal
//
//        return accountRepository.save(account);
//    }
//
//    // Method untuk mendapatkan akun berdasarkan nomor rekening
//    public Account getAccountByNumber(String accountNumber) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new AccountNotFoundException("Account not found");
//        }
//        return account;
//    }
//
//    // Method untuk menghasilkan nomor rekening acak
//    private String generateAccountNumber() {
//        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
//        Random random = new Random();
//        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
//            accountNumber.append(random.nextInt(10)); // Menambahkan angka acak (0-9)
//        }
//        return accountNumber.toString();
//    }
//
//    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
//        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
//        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
//
//        if (fromAccount == null || toAccount == null) {
//            throw new IllegalArgumentException("One or both accounts not found");
//        }
//
//        if (fromAccount.getBalance() == null || toAccount.getBalance() == null) {
//            throw new IllegalStateException("Account balance is missing");
//        }
//
//        if (fromAccount.getBalance().compareTo(amount) < 0) {
//            throw new IllegalArgumentException("Insufficient funds");
//        }
//
//        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
//        toAccount.setBalance(toAccount.getBalance().add(amount));
//
//        accountRepository.save(fromAccount);
//        accountRepository.save(toAccount);
//
//        // Log the transaction
//        transactionService.logTransaction(fromAccountNumber, toAccountNumber, amount);
//
//        return true;
//    }



/*
    ini yg asli
*/
//    public Account createAccount(AccountRequestDTO accountRequestDTO) {
//        User user = userRepository.findById(accountRequestDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Account account = new Account();
//        account.setAccountNumber(generateAccountNumber());
//        account.setAccountHolderName(accountRequestDTO.getAccountHolderName());
//        account.setUser(user);
//
//        return accountRepository.save(account);
//    }


    // Method untuk membuat akun baru dengan saldo
//    public Account createAccount(String accountNumber, String accountHolderName, BigDecimal initialBalance) {
//        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("Initial balance cannot be negative");
//        }
//
//        Account account = new Account();
//        account.setAccountNumber(accountNumber);
//        account.setAccountHolderName(accountHolderName);
//        account.setBalance(initialBalance); // Atur saldo awal
//
//        return accountRepository.save(account);
//    }

//    // Method untuk mendapatkan akun berdasarkan nomor rekening
//    public Account getAccountByNumber(String accountNumber) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new AccountNotFoundException("Account not found");
//        }
//        return account;
//    }
//
//
//    private String generateAccountNumber() {
//        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
//        Random random = new Random();
//        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
//            accountNumber.append(random.nextInt(10)); // Menambahkan angka acak (0-9)
//        }
//        return accountNumber.toString();
//    }


//    public Account getAccountByNumber(String accountNumber) {
//        return accountRepository.findByAccountNumber(accountNumber);
//    }


//    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
//        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
//        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
//
//        if (fromAccount == null || toAccount == null) {
//            throw new IllegalArgumentException("One or both accounts not found");
//        }
//
//        if (fromAccount.getBalance() == null || toAccount.getBalance() == null) {
//            throw new IllegalStateException("Account balance is missing");
//        }
//
//        if (fromAccount.getBalance().compareTo(amount) < 0) {
//            throw new IllegalArgumentException("Insufficient funds");
//        }
//
//        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
//        toAccount.setBalance(toAccount.getBalance().add(amount));
//
//        accountRepository.save(fromAccount);
//        accountRepository.save(toAccount);
//
//        // Log the transaction
//        transactionService.logTransaction(fromAccountNumber, toAccountNumber, amount);
//
//        return true;
//    }

