package com.bankingmanagement.service;

import com.bankingmanagement.BankingManagement;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    /**
     * Fetches bank details by bank name.
     * @param bankName
     * @return
     * @throws BankDetailsNotFoundException
     */
    public BankTO getBankByName(String bankName) throws BankDetailsNotFoundException {
        log.info("BankServiceImpl.getBankByName: Fetching bank details for bank name: {}", bankName);
        Bank bank = bankRepository.findByBankName(bankName);
        if (Objects.isNull(bank)) {
            log.info("BankServiceImpl.getBankByName: Bank details not found for name: {}", bankName);
            throw new BankDetailsNotFoundException("Bank details not found for name: " + bankName);
        }

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

    @Override
    public BankTO addBank(BankRequest bank) throws BankDetailsNotFoundException {
        log.info("BankServiceImpl.addBank: Adding new bank with name: {}", bank.getBankName());

        if (Objects.isNull(bank) || bank.getBankName().isEmpty() || bank.getBankAddress().isEmpty()) {
            log.error("BankServiceImpl.addBank: Invalid bank details provided");
            throw new BankDetailsNotFoundException("Invalid bank details provided");
        }

        Bank newBank = new Bank();
        newBank.setBankName(bank.getBankName());
        newBank.setBankAddress(bank.getBankAddress());

        Set<Branch> branches = bank.getBranchList().stream().map(branchTO -> {
            Branch branch = new Branch();
            branch.setBranchName(branchTO.getBranchName());
            branch.setBranchAddress(branchTO.getBranchAddress());
            branch.setBank(newBank);
            return branch;
        }).collect(Collectors.toSet());
        newBank.setBranchSet(branches);

        Bank saveBank = bankRepository.save(newBank);
        log.info("BankServiceImpl.addBank: Bank added successfully with code: {}", saveBank.getBankCode());
        return new BankTO(
                saveBank.getBankCode(),
                saveBank.getBankName(),
                saveBank.getBankAddress(),
                saveBank.getBranchSet().stream()
                        .map(branch -> new BranchTO(
                                branch.getBranchId(),
                                branch.getBranchName(),
                                branch.getBranchAddress()))
                        .collect(Collectors.toList())
        );
    }
}
