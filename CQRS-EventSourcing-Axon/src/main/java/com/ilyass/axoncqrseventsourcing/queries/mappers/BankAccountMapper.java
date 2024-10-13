package com.ilyass.axoncqrseventsourcing.queries.mappers;


import com.ilyass.axoncqrseventsourcing.queries.dto.AccountOperationResponseDTO;
import com.ilyass.axoncqrseventsourcing.queries.dto.BankAccountResponseDTO;
import com.ilyass.axoncqrseventsourcing.queries.entities.AccountOperation;
import com.ilyass.axoncqrseventsourcing.queries.entities.BankAccount;

import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public BankAccountResponseDTO bankAccountToBankAccountDTO(BankAccount bankAccount) {
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        bankAccountResponseDTO.setStatus(bankAccount.getStatus());
        bankAccountResponseDTO.setId(bankAccount.getId());
        bankAccountResponseDTO.setBalance(bankAccount.getBalance());
        return bankAccountResponseDTO;
    }

    ;

    public AccountOperationResponseDTO accountOperationToAccountOperationDTO(AccountOperation accountOperation) {
        AccountOperationResponseDTO accountOperationResponseDTO = new AccountOperationResponseDTO();
        accountOperationResponseDTO.setOperationDate(accountOperation.getOperationDate());
        accountOperationResponseDTO.setType(accountOperation.getType());
        accountOperationResponseDTO.setAmount(accountOperation.getAmount());
        accountOperationResponseDTO.setId(accountOperation.getId());
        return  accountOperationResponseDTO;
    }

    ;
}
