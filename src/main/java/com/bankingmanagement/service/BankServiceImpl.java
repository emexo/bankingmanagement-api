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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    public BankRepository bankRepository;

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
        return bankList.stream().map(this::convertToBankTO).collect(Collectors.toList());

    }

    /**
     * Converts a Bank entity to a BankTO object.
     *
     * @param bank the Bank entity to convert
     * @return the corresponding BankTO object
     */
    private BankTO convertToBankTO(Bank bank) {
        List<BranchTO> branchTOList = bank.getBranchSet() != null ?
                bank.getBranchSet().stream()
                        .map(branch -> new BranchTO(
                                branch.getBranchId(),
                                branch.getBranchName(),
                                branch.getBranchAddress()))
                        .collect(Collectors.toList())
                : List.of();

        return new BankTO(
                bank.getBankCode(),
                bank.getBankName(),
                bank.getBankAddress(),
                branchTOList
        );
    }
}
