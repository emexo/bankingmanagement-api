package com.bankingmanagement.mapper;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankMapper {

    public static BankTO covertToBankTO(Bank bank) {
        BankTO bankTO = new BankTO();
        bankTO.setBankCode(bank.getBankCode());
        bankTO.setBankName(bank.getBankName());
        bankTO.setBankAddress(bank.getBankAddress());
        List<BranchTO> branchTOList = bank.getBranch().stream()
                .map(BankMapper::convertToBranchTO).collect(Collectors.toList());
        bankTO.setBranches(branchTOList);
        return bankTO;
    }

    public static BranchTO convertToBranchTO(Branch branch) {
        BranchTO branchTO = new BranchTO();
        branchTO.setBranchId(branch.getBranchId());
        branchTO.setBranchName(branch.getBranchName());
        branchTO.setBranchAddress(branch.getBranchAddress());
        return branchTO;
    }
}

