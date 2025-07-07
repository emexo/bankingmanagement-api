package com.bankingmanagement.service;

import com.bankingmanagement.BankingManagement;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    public BankRepository bankRepository;


    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFoundException {
        log.info("BankServiceImpl.getAllBanks: Fetching all bank details");
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            log.info("BankServiceImpl.getAllBanks: No bank details found");
            throw new BankDetailsNotFoundException("No bank details found");
        }
        return banks.stream()
                .map(bank -> new BankTO(
                        bank.getBankCode(),
                        bank.getBankName(),
                        bank.getBankAddress(),
                        bank.getBranchSet().stream()
                                .map(branch -> new BranchTO(
                                        branch.getBranchId(),
                                        branch.getBranchName(),
                                        branch.getBranchAddress()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Fetches bank details by bank code.
     * @param bankCode
     * @return
     * @throws BankDetailsNotFoundException
     */
    @Override
    public BankTO getBankByCode(int bankCode) throws BankDetailsNotFoundException {
        log.info("BankServiceImpl.getBankByCode: Fetching bank details for bank code: {}", bankCode);
        Bank bank = bankRepository.findById(bankCode)
                .orElseThrow(() -> new BankDetailsNotFoundException("Bank details not found for code: " + bankCode));

        return new BankTO(
                bank.getBankCode(),
                bank.getBankName(),
                bank.getBankAddress(),
                bank.getBranchSet().stream()
                        .map(branch -> new BranchTO(
                                branch.getBranchId(),
                                branch.getBranchName(),
                                branch.getBranchAddress()))
                        .collect(Collectors.toList())
        );

    }
}
