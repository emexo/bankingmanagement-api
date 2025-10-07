package com.bankingmanagement.mapper;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.model.AddBankResponseTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.model.BranchTO;
import com.bankingmanagement.mongoentity.BankEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankMapper {

    /**
     * Converts a Bank entity to a BankTO object.
     *
     * @param bank the Bank entity to convert
     * @return the corresponding BankTO object
     */
    public BankTO convertToBankTO(BankEntity bank) {

        return new BankTO(
                bank.getBankCode(),
                bank.getBankName(),
                bank.getBankAddress(),
                branchTOList
        );
    }

    public Bank convertToBankEntity(BankRequest bankRequest) {
        Bank bank = new Bank();
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        if(!CollectionUtils.isEmpty(bankRequest.getBranchList())){
         Set<Branch> branchSet =   bankRequest.getBranchList().stream().map(branchRequest -> {
                     Branch branch = new Branch();
                     branch.setBranchName(branchRequest.getBranchName());
                     branch.setBranchAddress(branchRequest.getBranchAddress());
                     branch.setBank(bank);
                     return branch;
                 }
                 ).collect(Collectors.toSet());
            bank.setBranchSet(branchSet);
        }
        return bank;
    }

    public AddBankResponseTO convertToAddBankResponseTO(Bank bank) {
        return new AddBankResponseTO(bank.getBankCode(), "Bank added successfully");
    }

}
