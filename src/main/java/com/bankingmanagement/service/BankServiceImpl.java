package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BankServiceImpl implements BankService{

    public BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFoundException {
        log.info("BankServiceImpl.getAllBanks: Fetching all bank details");
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            log.info("BankServiceImpl.getAllBanks: No bank details found");
            throw new BankDetailsNotFoundException("No bank details found");
        }

      List<BankTO> bankTOS =  banks.stream().map(bank -> {
            BankTO bankTO = new BankTO(bank.getBankCode(), bank.getBankName(), bank.getBankAddress());
            return bankTO;
        }).collect(Collectors.toList());

        log.info("BankServiceImpl.getAllBanks: Successfully fetched bank details");
        return bankTOS;
    }
}
