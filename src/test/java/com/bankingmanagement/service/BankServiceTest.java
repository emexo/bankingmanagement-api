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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
    @Mock
    private BankRepository bankRepository;

    @Mock
    private  BankMapper bankMapper;

    @InjectMocks
    private BankServiceImpl bankService;


    @Test
    public void getAllBanks_whenBanksExist_returnsBankTOList() throws BankDetailsNotFoundException {
        Bank bank1 = new Bank();
        bank1.setBankCode(1);
        bank1.setBankName("Bank A");
        bank1.setBankAddress("Address A");

        when(bankRepository.findAll()).thenReturn(Collections.singletonList(bank1));

        List<BankTO> result = bankService.getAllBanks();
        assertEquals(1, result.size());
    }
}
