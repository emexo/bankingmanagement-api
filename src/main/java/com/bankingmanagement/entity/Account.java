package com.bankingmanagement.entity;

import jakarta.persistence.*;

@Table(name = "t_account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_sequence", allocationSize = 1)
    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_balance")
    private double accountBalance;

    @ManyToOne
    @JoinColumn(name = "Cust_ID")
    private Customer customer;

}
