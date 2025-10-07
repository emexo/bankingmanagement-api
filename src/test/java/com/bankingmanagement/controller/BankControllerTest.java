package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.*;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
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
        BankTO bankTO = new BankTO(1, "Bank1", "Address1");

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

    @Test
    public void getBankById_whenBankIsPresent_thenReturnBank() throws Exception {
        BranchTO branchTO = new BranchTO(1, "Branch1", "Address1");
        BankTO bankTO = new BankTO(1, "Bank1", "Address1");

        when(bankService.getBankById(anyString())).thenReturn(bankTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void addBank_whenValidInput_thenReturnCreated() throws Exception {
        AddBankResponseTO addBankResponseTO = new AddBankResponseTO(1, "Bank added successfully");
        when(bankService.addBank(any())).thenReturn(addBankResponseTO);

        BranchRequest branchRequest = new BranchRequest();
        branchRequest.setBranchName("Branch1");
        branchRequest.setBranchAddress("Address1");

        BankRequest bankRequest = new BankRequest();
        bankRequest.setBankName("Bank1");
        bankRequest.setBankAddress("Address1");
        bankRequest.setBranchList(Arrays.asList(branchRequest));

        ObjectMapper objectMapper = new ObjectMapper();
        String bankRequestJson = objectMapper.writeValueAsString(bankRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bankRequestJson)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
    }
}
