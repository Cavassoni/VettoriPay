package com.cavassoni.vettoripay.domain.mongodb.repository;

import com.cavassoni.vettoripay.domain.mongodb.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, UUID> {
}
