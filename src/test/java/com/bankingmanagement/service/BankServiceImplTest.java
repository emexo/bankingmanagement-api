package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotfoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private BankMapper bankMapper;

    @InjectMocks
    private BankServiceImpl bankService;

    @Test
    public void findAll_whenBankDetailsExist_thenReturnBankData() throws BankDetailsNotfoundException {
        List<Bank> bankList = new ArrayList<>();
        Bank bank = new Bank();
        bank.setBankName("SBI");
        bank.setBankCode(34);
        bank.setBandAddress("Bangalore");
        bankList.add(bank);

        when(bankRepository.findAll()).thenReturn(bankList);
        when(bankMapper.convertToBankTo(any())).thenReturn(Collections.singletonList(new BankTO(1, "SBI", "Marathalli")));

        List<BankTO>  bankTOS = bankService.findAll();
        assertEquals(1, bankTOS.size());
    }

    @Test
    public void findAll_whenBankDetailsNotExist_thenThrowException(){
        when(bankRepository.findAll()).thenReturn(null);
        assertThrows(BankDetailsNotfoundException.class, ()->bankService.findAll());
    }
}
