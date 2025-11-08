package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    // http://127.0.0.1:8080/api/v1/banks GET
    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks() throws BankDetailsNotFoundException {
        log.info("Received request to fetch all banks");
        List<BankTO> bankTOList = bankService.getAllBanks();
        log.info("Successfully retrieved {} banks", bankTOList.size());
        return ResponseEntity.ok(bankTOList);
    }

    @PostMapping
    public ResponseEntity<BankTO> createBank(@RequestBody @Valid BankRequest bankRequest) throws BankDetailsNotFoundException{
        log.info("Received request to create a new bank: {}", bankRequest.getBankName());
        BankTO createdBank = bankService.createBank(bankRequest);
        log.info("Successfully created bank with code: {}", createdBank.getBankCode());
        return ResponseEntity.ok(createdBank);

    }
}

