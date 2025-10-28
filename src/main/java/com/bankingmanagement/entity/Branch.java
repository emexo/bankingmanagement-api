package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_Branch")
public class Branch {

    @Id
    @Column(name="branch_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_seq")
    @SequenceGenerator(name = "branch_id_seq", sequenceName = "branch_id_sequence", allocationSize = 1)
    private int branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_address")
    private String branchAddress;

    @ManyToOne
    @JoinColumn(name="Bank_Code")
    private Bank bank;

}
