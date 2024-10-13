package com.ilyass.axoncqrseventsourcing.queries.dto;

import com.ilyass.axoncqrseventsourcing.commonapi.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperationResponseDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
}
