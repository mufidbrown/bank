package com.dbs.service;


import com.dbs.entity.TransactionLog;
import com.dbs.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class TransactionService {

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    public void logTransaction(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        TransactionLog log = new TransactionLog();
        log.setFromAccountNumber(fromAccountNumber);
        log.setToAccountNumber(toAccountNumber);
        log.setAmount(amount);
        log.setTransactionDate(new Timestamp(System.currentTimeMillis())); // Atur timestamp di sini
        transactionLogRepository.save(log);
    }

}
