package com.ilyass.axoncqrseventsourcing.queries.dto;

import com.ilyass.axoncqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccountResponseDTO {
    private String id;
    private double balance;
    private AccountStatus status;
}
