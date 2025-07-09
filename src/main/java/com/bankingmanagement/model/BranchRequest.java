package com.bankingmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchRequest {
    @NotNull
    private String branchName;
    private String branchAddress;
}
