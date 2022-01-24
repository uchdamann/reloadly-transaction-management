package com.reloadly.devops.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
