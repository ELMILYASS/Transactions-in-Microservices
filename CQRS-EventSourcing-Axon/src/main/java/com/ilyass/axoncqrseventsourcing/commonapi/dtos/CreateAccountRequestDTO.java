package com.ilyass.axoncqrseventsourcing.commonapi.dtos;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateAccountRequestDTO {
    private double balance;
    private String currency;
}
