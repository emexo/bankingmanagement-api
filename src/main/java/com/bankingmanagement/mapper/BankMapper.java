package com.bankingmanagement.mapper;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.model.BankTO;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;
import java.util.stream.Collectors;

public class BankMapper {
    public List<BankTO> convertToBankTo(List<Bank> bankList){
        List<BankTO> bankTOList = bankList.stream().map(bank -> {
            BankTO bankTO = new BankTO(bank.getBankCode(), bank.getBankName(), bank.getBandAddress());
            return bankTO;
        }).collect(Collectors.toList());

        return bankTOList;
    }
}
