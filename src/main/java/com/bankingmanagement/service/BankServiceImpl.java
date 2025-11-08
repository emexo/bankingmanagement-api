package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
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
     * @throws BankDetailsNotFoundException
     */
    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFoundException {
        log.info("Fetching all bank details");
        List<Bank> bankList = bankRepository.findAll();

        if(!CollectionUtils.isEmpty(bankList)){
            log.error("No bank details found");
            throw new BankDetailsNotFoundException("No bank details found");
        }
        // Convert using mapper
        List<BankTO> bankTOList = bankList.stream().map(BankMapper::covertToBankTO).toList();

        log.info("Successfully fetched {} bank details", bankTOList.size());
        return bankTOList;
    }

    @Override
    public BankTO createBank(com.bankingmanagement.model.BankRequest bankRequest) throws BankDetailsNotFoundException {
        log.info("Creating a new bank with name: {}", bankRequest);

        // Convert BankRequest to Bank entity
        Bank bankEntity = bankMapper.convertToBankEntity(bankRequest);
        Bank savedBank = bankRepository.save(bankEntity);
        log.info("Successfully created bank with code: {}", savedBank.getBankCode());

        return bankMapper.covertToBankTO(savedBank);

    }
}
