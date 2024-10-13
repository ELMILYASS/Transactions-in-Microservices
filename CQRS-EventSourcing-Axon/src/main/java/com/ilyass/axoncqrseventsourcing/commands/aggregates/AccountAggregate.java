package com.ilyass.axoncqrseventsourcing.commands.aggregates;

import com.ilyass.axoncqrseventsourcing.commonapi.commands.CreateAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.commands.CreditAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.commands.DebitAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.enums.AccountStatus;
import com.ilyass.axoncqrseventsourcing.commonapi.events.*;
import com.ilyass.axoncqrseventsourcing.commonapi.exceptions.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    //@Id
    private String accountId;
    private double balance;
    private String currency;
    private String status;

    public AccountAggregate() {
        // required by Axon
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        log.info("CreateAccountCommand Received");
        if(createAccountCommand.getBalance()<=0) throw  new InsufficientBalanceException("Negative Balance ");

        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                        createAccountCommand.getId(),
                        createAccountCommand.getBalance(),
                        createAccountCommand.getCurrency(),
                        AccountStatus.CREATED
                )
        );
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("AccountCreatedEvent Occured");
        this.accountId=accountCreatedEvent.getId();
        this.balance=accountCreatedEvent.getBalance();
        this.currency=accountCreatedEvent.getCurrency();
        this.status=String.valueOf(accountCreatedEvent.getStatus());
        AggregateLifecycle.apply(new AccountActivatedEvent(this.accountId,AccountStatus.ACTIVATED));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("AccountActivatedEvent Occured");
        this.status=String.valueOf(accountActivatedEvent.getStatus());
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        System.out.println("/////////// account //////////");
        System.out.println(this.accountId);
        if((this.balance - (debitAccountCommand.getAmount())<0)){
            throw new InsufficientBalanceException("Insufficient Balance=>"+this.balance);
        } else if (debitAccountCommand.getAmount()<0) {
            throw new InsufficientBalanceException("Negative Amount");
        } else
            AggregateLifecycle.apply(
                    new AccountDebitedEvent(
                            debitAccountCommand.getId(),
                            debitAccountCommand.getAmount(),
                            debitAccountCommand.getCurrency()
                    )
            );
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("AccountDebitedEvent Occured");
        this.balance=this.balance - accountDebitedEvent.getAmount();
        System.out.println(this.balance);
    }
    @EventSourcingHandler
    public void on(AccountHeldEvent accountHeldEvent){
        this.status=String.valueOf(accountHeldEvent.getStatus());
    }
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance=this.balance + accountCreditedEvent.getAmount();
        log.info("==================");
        log.info(String.valueOf(this.balance));
        log.info("==================");
    }
}
