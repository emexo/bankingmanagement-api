package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
@Validated
public class BankController {

    @Autowired
    private BankService bankService;
    //http://localhost:8080/api/v1/banks GET
    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks() throws BankDetailsNotFoundException{
        log.info("Received request to fetch all banks");
        List<BankTO> bankTOList = bankService.getAllBanks();
        log.info("Successfully fetched all banks");
        return new ResponseEntity<>(bankTOList, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/banks/1 GET  @PathVariable
    //http://localhost:8080/api/v1/banks?code=1 GET  @RequestParam
    @GetMapping("/{code}")
    public ResponseEntity<BankTO> getBankById(@PathVariable("code") @Positive(message = "Bank code must be a positive number") int bankCode) throws BankDetailsNotFoundException {
        log.info("Received request to fetch bank with code: {}", bankCode);
        BankTO bankTO = bankService.getBankById(bankCode);
            log.info("Successfully fetched bank with code: {}", bankCode);
            return new ResponseEntity<>(bankTO, HttpStatus.OK);

    }
}
