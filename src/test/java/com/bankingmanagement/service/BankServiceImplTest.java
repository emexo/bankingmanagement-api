package com.bankingmanagement.service;

import com.bankingmanagement.BankingManagement;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    public BankRepository bankRepository;

    @InjectMocks
    public BankServiceImpl bankService;

    @Test
    public void getAllBanks_whenBanksExist_thenReturnBankList() throws BankDetailsNotFoundException {
        List<Bank> banks = new ArrayList<>();
        Bank bank1 = new Bank();
        bank1.setBankCode(1);
        bank1.setBankName("Bank A");
        bank1.setBankAddress("Address A");
        Bank bank2 = new Bank();
        bank2.setBankCode(2);
        bank2.setBankName("Bank B");
        bank2.setBankAddress("Address B");

        banks.add(bank1);
        banks.add(bank2);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankTO> bankTOS= bankService.getAllBanks();
        assertEquals(2, bankTOS.size());
    }

    @Test
    public void getAllBanks_whenBranchExist_thenReturnBankList() throws BankDetailsNotFoundException {
        List<Bank> banks = new ArrayList<>();
        Bank bank1 = new Bank();
        bank1.setBankCode(1);
        bank1.setBankName("Bank A");
        bank1.setBankAddress("Address A");

        Branch branch1 = new Branch();
        branch1.setBranchId(101);
        branch1.setBranchName("Branch A1");
        branch1.setBranchAddress("Branch Address A1");
        Set<Branch> branches1 = new HashSet<>();
        branches1.add(branch1);
        bank1.setBranchSet(branches1);

        Bank bank2 = new Bank();
        bank2.setBankCode(2);
        bank2.setBankName("Bank B");
        bank2.setBankAddress("Address B");

        banks.add(bank1);
        banks.add(bank2);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankTO> bankTOS= bankService.getAllBanks();
        assertEquals(2, bankTOS.size());
    }

    @Test
    public void getAllBanks_whenNoBanksExist_thenThrowException() {
       when(bankRepository.findAll()).thenReturn(null);

       assertThrows(NullPointerException.class , ()-> bankService.getAllBanks());
    }

    @Test
    public void getAllBanks_whenNoBanksExist_thenThrowBankDetailsException() {
        when(bankRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(BankDetailsNotFoundException.class , ()-> bankService.getAllBanks());
    }
}
