package com.dbs.service;

import com.dbs.entity.Account;
import com.dbs.entity.TransactionLog;
import com.dbs.repository.AccountRepository;
import com.dbs.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Transactional
    public void transfer(Long senderAccountId, Long receiverAccountId, BigDecimal amount) {
        Account sender = accountRepository.findById(senderAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account receiver = accountRepository.findById(receiverAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        TransactionLog log = new TransactionLog();
        log.setSenderAccountId(senderAccountId);
        log.setReceiverAccountId(receiverAccountId);
        log.setAmount(amount);
        log.setTimestamp(LocalDateTime.now());

        transactionLogRepository.save(log);
    }
}