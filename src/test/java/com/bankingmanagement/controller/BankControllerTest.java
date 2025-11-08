package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankService bankService;

    @Test
    public void getAllBanks_whenBanksExist_returnsBankList() throws Exception {
        BankTO bankTO = new BankTO();
        bankTO.setBankCode(234);
        bankTO.setBankName("Test Bank");
        bankTO.setBankAddress("123 Test St, Test City");

        when(bankService.getAllBanks()).thenReturn(Collections.singletonList(bankTO));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .accept("application/json");

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllBanks_whenBanksDetailsNotExist_throwBankDetailsNotFound() throws Exception {
        when(bankService.getAllBanks()).thenThrow(BankDetailsNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
                .accept("application/json");

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
