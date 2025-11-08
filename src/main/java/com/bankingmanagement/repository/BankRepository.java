package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    //query methods can be defined here if needed
    //query annotations can also be used for custom queries
    List<Bank> findByBankName(String bankName);

    @Query("Select bank from Bank bank where bank.bankName=?1")
    List<Bank> findByName( String bankName);
}
