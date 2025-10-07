package com.bankingmanagement.service;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.AddBankResponseTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;

import java.util.List;

public interface BankService {
    List<BankTO> getAllBanks() throws BankDetailsNotFoundException;
    BankTO getBankById(String bankCode) throws BankDetailsNotFoundException;
    AddBankResponseTO addBank(BankRequest bankRequest) throws BankDetailsNotFoundException;
    void evictAllBanksCache();
}
