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

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private BankService bankService;


    @Test
    public void getAllBanks_whenBanksArePresent_thenReturnListOfBanks() throws Exception {
        BranchTO branchTO = new BranchTO(1, "Branch1", "Address1");
        BankTO bankTO = new BankTO(1, "Bank1", "Address1", Arrays.asList(branchTO));

        when(bankService.getAllBanks()).thenReturn(Arrays.asList(bankTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllBanks_whenBankDetailsNorPresent_thenThrowException() throws Exception{
        when(bankService.getAllBanks()).thenThrow(BankDetailsNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
