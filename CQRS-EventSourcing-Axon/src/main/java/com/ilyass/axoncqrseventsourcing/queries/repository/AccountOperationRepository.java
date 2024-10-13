package com.ilyass.axoncqrseventsourcing.queries.repository;

import com.ilyass.axoncqrseventsourcing.queries.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
}
