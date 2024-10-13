package com.ilyass.axoncqrseventsourcing.commands.controllers;


import com.ilyass.axoncqrseventsourcing.commands.services.AccountCommandService;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreateAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.dtos.DebitAccountRequestDTO;
import com.ilyass.axoncqrseventsourcing.commonapi.exceptions.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands")
@AllArgsConstructor
public class AccountCommandRestController {
    private final EventStore eventStore;
    private final AccountCommandService accountCommandService;

    @PostMapping(path = "/accounts/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO accountRequestDTO) {
        return accountCommandService.createAccount(accountRequestDTO);
    }

    @PutMapping(path = "/accounts/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO) {
        return accountCommandService.debitAccount(debitAccountRequestDTO);
    }

    @PutMapping(path = "/accounts/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO) {
        return accountCommandService.creditAccount(creditAccountRequestDTO);
    }

    @GetMapping("/eventstore/{id}")
    public Stream eventStore(@PathVariable String id) {
        return eventStore.readEvents(id).asStream();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> exceptionHandler(InsufficientBalanceException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
