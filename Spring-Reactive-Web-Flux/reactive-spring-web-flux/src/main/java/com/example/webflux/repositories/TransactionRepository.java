package com.example.webflux.repositories;

import com.example.webflux.entities.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, Long> {
    Flux<Transaction> findByCompanyId(Long companyId);
}
