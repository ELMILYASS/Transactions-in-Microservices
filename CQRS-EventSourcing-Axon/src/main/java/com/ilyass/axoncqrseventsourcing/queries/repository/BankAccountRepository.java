package com.ilyass.axoncqrseventsourcing.queries.repository;

import com.ilyass.axoncqrseventsourcing.queries.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
