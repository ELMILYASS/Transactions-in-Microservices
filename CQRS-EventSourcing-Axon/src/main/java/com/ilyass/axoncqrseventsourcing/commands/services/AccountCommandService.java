package com.ilyass.axoncqrseventsourcing.commands.services;



import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreateAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.DebitAccountRequestDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {
    CompletableFuture<String> createAccount(CreateAccountRequestDTO accountRequestDTO);
    CompletableFuture<String> debitAccount(DebitAccountRequestDTO debitAccountRequestDTO);
    CompletableFuture<String> creditAccount(CreditAccountRequestDTO creditAccountRequestDTO);
}
