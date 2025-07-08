package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    /**
     * Finds a bank by its name.
     *
     * @param bankName the name of the bank
     * @return the Bank entity if found, otherwise null
     */
    Bank findByBankName(String bankName);

    /**
     * Custom query to find a bank by its name.
     *
     * @param bankName the name of the bank
     * @return the Bank entity if found
     */
    @Query(value = "SELECT b FROM Bank b WHERE b.bankName = ?1")
    Bank findByName(String bankName);
}
