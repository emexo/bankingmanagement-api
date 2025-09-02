package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.mapper.BankMapper;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    public BankRepository bankRepository;

    @Autowired
    private BankMapper bankMapper;

    /**
     * Retrieves all banks from the repository and converts them to BankTO objects.
     *
     * @return a list of BankTO objects representing all banks
     * @throws BankDetailsNotFoundException if no bank details are found
     */
    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFoundException {
        log.info("Fetching all bank details");
        List<Bank> bankList = bankRepository.findAll();
        if(bankList.isEmpty()) {
            log.error("No bank details found");
            throw new BankDetailsNotFoundException("No bank details found");
        }
        return bankList.stream().map(bankMapper::convertToBankTO).collect(Collectors.toList());

    }

    @Override
    public BankTO getBankById(int bankCode) throws BankDetailsNotFoundException {
        log.info("Fetching bank details for bank code: {}", bankCode);
        Bank bank = bankRepository.findById(bankCode)
                .orElseThrow(() -> {
                    log.error("Bank details not found for bank code: {}", bankCode);
                    return new BankDetailsNotFoundException("Bank details not found for bank code: " + bankCode);
                });
        return bankMapper.convertToBankTO(bank);
    }
}
