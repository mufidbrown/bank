package com.dbs.entity;

import lombok.*;

import javax.persistence.*;
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


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relasi dengan User

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<TransactionLog> transactionLogs;


}
