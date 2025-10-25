package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotfoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Log4j2
@Service
public class BankServiceImpl implements  BankService{

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankMapper bankMapper;

    @Override
    public List<BankTO> findAll() throws BankDetailsNotfoundException {
        log.info("Inside the BankController.findAll");
        List<Bank> bankList = bankRepository.findAll();
        if (CollectionUtils.isEmpty(bankList)) {
            log.info("Bank details not exist");
            throw new BankDetailsNotfoundException("Bank details not found");
        }

        return bankMapper.convertToBankTo(bankList);
    }
}
