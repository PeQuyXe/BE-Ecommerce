package com.example.demo.repository;


import com.example.demo.model.MomoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomoTransactionRepository extends JpaRepository<MomoTransaction, String> {
}
