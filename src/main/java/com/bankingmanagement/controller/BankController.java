package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;
    //http://localhost:8080/api/v1/banks GET
    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks(){
        log.info("Received request to fetch all banks");
        List<BankTO> bankTOList;
        try {
            bankTOList = bankService.getAllBanks();
            log.info("Successfully fetched all banks");
            return new ResponseEntity<>(bankTOList, HttpStatus.OK);
        } catch (BankDetailsNotFoundException e) {
            log.error("Error fetching bank details: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Internal server error: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
