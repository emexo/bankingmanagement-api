package com.bankingmanagement.service;

import com.bankingmanagement.exception.BankDetailsNotfoundException;
import com.bankingmanagement.model.BankTO;

import java.util.List;

public interface BankService {
    List<BankTO> getAllBanks() throws BankDetailsNotfoundException;
}
