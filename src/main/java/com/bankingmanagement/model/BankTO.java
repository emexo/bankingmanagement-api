package com.bankingmanagement.model;

import java.util.List;

public record BankTO(
        int bankCode,
        String bankName,
        String bankAddress) {
}
