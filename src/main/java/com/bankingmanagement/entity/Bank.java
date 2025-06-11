package com.bankingmanagement.entity;

import jakarta.persistence.*;

@Table(name ="t_bank")
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_name_seq")
    @SequenceGenerator(name = "bank_name_seq", sequenceName = "bank_code_sequence", allocationSize = 1)
    @Column(name = "bank_code")
    private int bankCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_address")
    private String bankAddress;
}
