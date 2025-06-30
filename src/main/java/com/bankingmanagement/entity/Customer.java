package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Table(name = "t_customer")
@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_sequence", allocationSize = 1)
    @Column(name = "Cust_ID")
    private int customerId;

    @Column(name = "Cust_Name")
    private String customerName;

    @Column(name = "Cust_Phone")
    private String customerPhone;

    @Column(name = "Cust_Address")
    private String CustomerAddress;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts;
}
