package com.bankingmanagement.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankRequest {
    @NonNull
    private String bankName;
    private String bankAddress;
    private List<BranchRequest> branches;
}
