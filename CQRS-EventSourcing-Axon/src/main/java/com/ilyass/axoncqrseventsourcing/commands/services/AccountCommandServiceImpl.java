package com.ilyass.axoncqrseventsourcing.commands.services;
import com.ilyass.axoncqrseventsourcing.commonapi.commands.CreateAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.commands.CreditAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.commands.DebitAccountCommand;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreateAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    @Autowired
    private CommandGateway commandGateway;
    @Override
    public CompletableFuture<String> createAccount(CreateAccountRequestDTO accountRequestDTO) {
        return commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(), accountRequestDTO.getBalance(), accountRequestDTO.getCurrency()));
    }

    @Override
    public CompletableFuture<String> debitAccount(DebitAccountRequestDTO debitAccountRequestDTO) {
        return commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDTO.getAccountId(),
                debitAccountRequestDTO.getAmount(),
                debitAccountRequestDTO.getCurrency()
        ));
    }

    @Override
    public CompletableFuture<String> creditAccount(CreditAccountRequestDTO creditAccountRequestDTO) {
         return commandGateway.send(new CreditAccountCommand(
                 creditAccountRequestDTO.getAccountId(),
                 creditAccountRequestDTO.getAmount(),
                 creditAccountRequestDTO.getCurrency()
        ));
    }
}
