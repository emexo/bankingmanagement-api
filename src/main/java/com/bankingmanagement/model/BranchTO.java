package com.bankingmanagement.model;

import java.util.List;

public record BranchTO(
        int branchId,
        String branchName,
        String branchAddress) {
}
