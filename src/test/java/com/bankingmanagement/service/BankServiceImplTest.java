package com.bankingmanagement.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    BankRepository bankRepository;

    @InjectMocks
    BankServiceImpl bankServiceImpl;

    @Test
    public void getAllBanks_whenBankWithBranchExists_thenReturnBankWithBranch() throws BankDetailsNotFoundException {
        // Mock branch
        Branch mockBranch = mock(Branch.class);
        when(mockBranch.getBranchId()).thenReturn(101);
        when(mockBranch.getBranchName()).thenReturn("Main Branch");
        when(mockBranch.getBranchAddress()).thenReturn("123 Main St");

        // Mock bank
        Bank mockBank = mock(Bank.class);
        when(mockBank.getBankCode()).thenReturn(1);
        when(mockBank.getBankName()).thenReturn("Test Bank");
        when(mockBank.getBankAddress()).thenReturn("Test Address");
        HashSet<Branch> branchSet = new HashSet<>();
        branchSet.add(mockBranch);
        when(mockBank.getBranchSet()).thenReturn(branchSet);

        List<Bank> mockBanks = new ArrayList<>();
        mockBanks.add(mockBank);
        when(bankRepository.findAll()).thenReturn(mockBanks);

        List<BankTO> bankTOS = bankServiceImpl.getAllBanks();
        assertEquals(1, bankTOS.size());
        BankTO bankTO = bankTOS.get(0);
        assertEquals("Test Bank", bankTO.bankName());
        assertEquals(1, bankTO.branchList().size());
        assertEquals("Main Branch", bankTO.branchList().get(0).branchName());
        assertEquals("123 Main St", bankTO.branchList().get(0).branchAddress());
    }

    @Test
    public void getAllBanks_whenBanksDetailsExist_thenReturnBankList() throws BankDetailsNotFoundException {
        List<Bank> mockBanks = new ArrayList<>();
        Bank mockBank = mock(Bank.class);
        when(mockBank.getBankCode()).thenReturn(1);
        when(mockBank.getBankName()).thenReturn("Test Bank");
        when(mockBank.getBankAddress()).thenReturn("Test Address");
        when(mockBank.getBranchSet()).thenReturn(null);

        mockBanks.add(mockBank);
        when(bankRepository.findAll()).thenReturn(mockBanks);

        List<BankTO> bankTOS = bankServiceImpl.getAllBanks();
        assertEquals(1, bankTOS.size());
    }

    @Test
    public void getAllBanks_whenBankDetailsNotExist_thenThrowException(){
        when(bankRepository.findAll()).thenReturn(null);
        assertThrows(BankDetailsNotFoundException.class, ()-> bankServiceImpl.getAllBanks());
    }

    @Test
    public void getAllBanks_whenBankDetailsEmpty_thenThrowException() {
        when(bankRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(BankDetailsNotFoundException.class, () -> bankServiceImpl.getAllBanks());
    }
}
