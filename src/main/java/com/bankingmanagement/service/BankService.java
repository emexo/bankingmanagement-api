package com.bankingmanagement.service;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankTO;

import java.util.List;

public interface BankService {
    List<BankTO> getAllBanks() throws BankDetailsNotFoundException;

    BankTO getBankByCode(int bankCode) throws BankDetailsNotFoundException;

    BankTO getBankByName(String bankName) throws BankDetailsNotFoundException;
}
