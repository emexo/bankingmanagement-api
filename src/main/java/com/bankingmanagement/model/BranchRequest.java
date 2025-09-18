package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchRequest {
    @NotNull
    private String branchName;
    private String branchAddress;
}
