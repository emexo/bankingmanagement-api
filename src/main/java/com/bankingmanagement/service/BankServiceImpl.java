package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.AddBankResponseTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.mongoentity.BankEntity;
import com.bankingmanagement.mongorepository.BankMongoRepository;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    public BankRepository bankRepository;

    @Autowired
    private BankMongoRepository bankMongoRepository;

    @Autowired
    private BankMapper bankMapper;

    /**
     * Retrieves all banks from the repository and converts them to BankTO objects.
     *
     * @return a list of BankTO objects representing all banks
     * @throws BankDetailsNotFoundException if no bank details are found
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFoundException {
        log.info("Fetching all bank details");
        List<BankEntity> bankList = bankMongoRepository.findAll();
        if(bankList.isEmpty()) {
            log.error("No bank details found");
            throw new BankDetailsNotFoundException("No bank details found");
        }
        return bankList.stream().map(bankMapper::convertToBankTO).collect(Collectors.toList());

    }

    @Cacheable(cacheNames = "banks", key = "#bankCode")
    @Override
    public BankTO getBankById(String id) throws BankDetailsNotFoundException {
        log.info("Fetching bank details for bank code: {}", id);
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BankEntity bank = bankMongoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Bank details not found for bank code: {}", id);
                    return new BankDetailsNotFoundException("Bank details not found for bank code: " + id);
                });
        return bankMapper.convertToBankTO(bank);
    }

    @Override
    public AddBankResponseTO addBank(BankRequest bankRequest) throws BankDetailsNotFoundException {
       log.info("Adding new bank with details: {}", bankRequest);
       Bank bank = bankMapper.convertToBankEntity(bankRequest);
       Bank savedBank = bankRepository.save(bank);
       if(Objects.isNull(savedBank)){
           log.error("Failed to add bank");
           throw new BankDetailsNotFoundException("Failed to add bank");
       }
         return bankMapper.convertToAddBankResponseTO(savedBank);
    }

    @CacheEvict(value = "banks", allEntries = true)
    public void evictAllBanksCache(){

    }
}
