package com.dbs.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String accountHolderName;
//    private String balance;
    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relasi dengan User

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<TransactionLog> transactionLogs;


}
