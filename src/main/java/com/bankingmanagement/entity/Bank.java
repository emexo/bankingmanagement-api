package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Table(name = "T_Bank")
@Entity
public class Bank {
    @Id
    @Column(name = "Bank_Code")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_code_seq")
    @SequenceGenerator(name = "bank_code_seq", sequenceName = "bank_code_sequence", allocationSize = 1)
    private int bankCode;

    @Column(name = "Bank_Name")
    private String bankName;

    @Column(name = "Bank_Address")
    private String bankAddress;

    @OneToMany(mappedBy = "bank")
    private Set<Branch> branch;
}
