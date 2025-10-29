package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    // http://127.0.0.1:8080/api/v1/banks GET
    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks() {
        log.info("Received request to fetch all banks");
        List<BankTO> bankTOList = null;

        try {
            bankTOList = bankService.getAllBanks();
            log.info("Successfully retrieved {} banks", bankTOList.size());
        } catch (BankDetailsNotFoundException ex){
            log.error("Error fetching bank details: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception ex1){
            log.error("Unexpected error: {}", ex1.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        log.info("Returning bank details response");
        return ResponseEntity.ok(bankTOList);
    }
}
