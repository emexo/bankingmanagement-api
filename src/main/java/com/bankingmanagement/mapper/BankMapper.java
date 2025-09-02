package com.bankingmanagement.mapper;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankMapper {

    /**
     * Converts a Bank entity to a BankTO object.
     *
     * @param bank the Bank entity to convert
     * @return the corresponding BankTO object
     */
    public BankTO convertToBankTO(Bank bank) {
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
