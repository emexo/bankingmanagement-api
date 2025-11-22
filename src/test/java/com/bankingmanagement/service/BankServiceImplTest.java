package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {
    @Mock
    private  BankRepository bankRepository;

    @Mock
    private  BankMapper bankMapper;

    @InjectMocks
    private BankServiceImpl bankServiceImpl;

    @Test
    public void getAllBanks_whenBanksExist_returnsBankTOList() throws BankDetailsNotFoundException {
        List<Bank> bankList = new ArrayList<>();
        Bank bank = new Bank();
        bank.setBankCode(1);
        bank.setBankName("Test Bank");
        bank.setBankAddress("123 Test St");
        bankList.add(bank);

        when(bankRepository.findAll()).thenReturn(bankList);

        List<BankTO> bankTOList = bankServiceImpl.getAllBanks();
        assertEquals(1, bankTOList.size());
        assertEquals("Test Bank", bankTOList.get(0).getBankName());
    }

    @Test
    public void getAllBanks_whenNoBanksExist_throwsBankDetailsNotFoundException() throws BankDetailsNotFoundException {
        List<Bank> bankList = new ArrayList<>();
        when(bankRepository.findAll()).thenReturn(bankList);

        assertThrows(BankDetailsNotFoundException.class, () -> bankServiceImpl.getAllBanks());

    }
}
