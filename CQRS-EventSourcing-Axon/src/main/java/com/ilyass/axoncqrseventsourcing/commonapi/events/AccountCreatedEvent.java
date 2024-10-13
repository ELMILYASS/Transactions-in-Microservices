package com.ilyass.axoncqrseventsourcing.commonapi.events;

import com.ilyass.axoncqrseventsourcing.commonapi.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;
public class AccountCreatedEvent extends BaseEvent<String> {
    @Getter private final double balance;
    @Getter private final String currency;
    @Getter private final AccountStatus status;

    public AccountCreatedEvent(String id, double balance, String currency, AccountStatus status) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }
}
