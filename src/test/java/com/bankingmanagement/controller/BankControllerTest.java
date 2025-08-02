package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchRequest;
import com.bankingmanagement.service.BankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BankService bankService;
    
    
    @Test
    public void getAllBanks_whenBanksExist_thenReturnBanks() throws Exception {
        BankTO mockBankTO = new BankTO(1, "Test Bank", "Test Address", List.of());
        when(bankService.getAllBanks()).thenReturn(List.of(mockBankTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                 .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bankCode").value(1))
                .andExpect(jsonPath("$[0].bankName").value("Test Bank"))
                .andExpect(jsonPath("$[0].bankAddress").value("Test Address"));
    }

    @Test
    public void getAllBanks_whenNoBanksExist_thenReturnEmptyList() throws Exception {
        when(bankService.getAllBanks()).thenThrow(BankDetailsNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                 .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void getAllBanks_whenServiceThrowsException_thenReturnInternalServerError() throws Exception {
        when(bankService.getAllBanks()).thenThrow(new RuntimeException("Internal Server Error"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                 .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    @Test
    public void getBankByCode_whenBankExists_thenReturnBank() throws Exception {
        BankTO mockBankTO = new BankTO(1, "Test Bank", "Test Address", List.of());
        when(bankService.getBankByCode(anyInt())).thenReturn(mockBankTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks/1")
                 .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.bankCode").value(1))
                .andExpect(jsonPath("$.bankName").value("Test Bank"))
                .andExpect(jsonPath("$.bankAddress").value("Test Address"));
    }


    @Test
    public void addBank_whenValidBankRequest_thenReturnBankDetails() throws Exception {
        BranchRequest mockBranchRequest = new BranchRequest();
        mockBranchRequest.setBranchName("Main Branch");
        mockBranchRequest.setBranchAddress("123 Main St");

        BankRequest mockBankRequest = new BankRequest();
        mockBankRequest.setBankName("Test Bank");
        mockBankRequest.setBankAddress("Test Address");
        mockBankRequest.setBranchList(List.of(mockBranchRequest));

        BankTO mockAddedBank = new BankTO(1, "Test Bank", "Test Address", List.of());
        when(bankService.addBank(any(BankRequest.class))).thenReturn(mockAddedBank);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(mockBankRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isCreated());

    }

}
