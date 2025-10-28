package com.bankingmanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BranchTO {
    private int branchId;
    private String branchName;
    private String branchAddress;
    private List<BranchTO> branches;
}
