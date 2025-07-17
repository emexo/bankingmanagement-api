package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import com.bankingmanagement.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BankService bankService;

    @Test
    public void getAllBanks_whenBanksExist_thenReturnBanks() throws Exception {
        BranchTO branchTO = new BranchTO(1, "Branch A", "Bangalore");
        BankTO bank1 = new BankTO(1, "Address A", "Bangalore", Collections.singletonList(branchTO));

        when(bankService.getAllBanks()).thenReturn(Collections.singletonList(bank1));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllBanks_whenNoBanksExist_thenReturnNotFound() throws Exception {
        when(bankService.getAllBanks()).thenThrow(new BankDetailsNotFoundException("No banks found"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void getBankByCode_whenBankExists_thenReturnBank() throws Exception {
        BranchTO branchTO = new BranchTO(1, "Branch A", "Bangalore");
        BankTO bank = new BankTO(1, "Address A", "Bangalore", Collections.singletonList(branchTO));

        when(bankService.getBankByCode(anyInt())).thenReturn(bank);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

}
