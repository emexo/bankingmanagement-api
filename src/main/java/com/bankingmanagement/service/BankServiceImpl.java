package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotfoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class BankServiceImpl implements BankService{

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }
    /**
     * Get all banks
     * @return
     * @throws BankDetailsNotfoundException
     */
    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotfoundException {
        log.info("Fetching all bank details");
        List<Bank> bankList = bankRepository.findAll();

        if(CollectionUtils.isEmpty(bankList)){
            log.error("No bank details found");
            throw new BankDetailsNotfoundException("No bank details found");
        }
        // Convert using mapper
        List<BankTO> bankTOList = bankList.stream().map(BankMapper::covertToBankTO).toList();

        log.info("Successfully fetched {} bank details", bankTOList.size());
        return bankTOList;
    }
}
