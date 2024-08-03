package com.dbs.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions_logs")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private double amount;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;

    @Column(nullable = false)
    private Timestamp transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
