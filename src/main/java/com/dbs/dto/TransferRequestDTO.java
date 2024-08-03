package com.dbs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {

    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;

}
