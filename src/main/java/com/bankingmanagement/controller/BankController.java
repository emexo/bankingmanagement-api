package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotfoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    //http://127.0.0.1:8080/api/v1/banks GET
    @GetMapping
    public ResponseEntity<List<BankTO>> findAll(){
        log.info("Inside the BankController.findAll");
        List<BankTO> bankTOS;
        try{
            bankTOS = bankService.findAll();
            log.info("Bank details:{}", bankTOS);
        }catch (BankDetailsNotfoundException ex){
            log.info("Bank details not exist");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex1){
            log.info("Exception while getting the bank details");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.findAll()");
        return new ResponseEntity<>(bankTOS, HttpStatus.OK);
    }
}
