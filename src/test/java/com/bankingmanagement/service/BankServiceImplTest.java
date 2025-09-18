package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import com.bankingmanagement.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    public BankRepository bankRepository;

    @Mock
    private BankMapper bankMapper;

    @InjectMocks
    public BankServiceImpl bankService;


    @Test
    public void getAllBanks_whenBankDetailsExist_thenReturnBankList() throws BankDetailsNotFoundException {
        List<Bank> bankList = new ArrayList<>();
        Bank bank = new Bank();
        bank.setBankCode(1);
        bank.setBankName("Test Bank");
        bank.setBankAddress("123 Test St");
        bankList.add(bank);

        when(bankRepository.findAll()).thenReturn(bankList);
        BankTO bankTO = new BankTO(1, "Test Bank", "123 Test St",
                Arrays.asList(new BranchTO(1, "Test Branch", "456 Branch St")));
        when(bankMapper.convertToBankTO(any())).thenReturn(bankTO);

        List<BankTO> bankTOS = bankService.getAllBanks();
        assertEquals(1, bankTOS.size());
        assertEquals("Test Bank", bankTOS.get(0).bankName());
    }

    @Test
    public void getAllBanks_whenBankDetailsNotExist_thenThrowException(){
        when(bankRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(BankDetailsNotFoundException.class, ()-> bankService.getAllBanks());
    }
}
