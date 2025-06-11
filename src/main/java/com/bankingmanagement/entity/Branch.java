package com.bankingmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="t_branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_seq")
    @SequenceGenerator(name = "branch_id_seq", sequenceName = "branch_id_sequence", allocationSize = 1)
    @Column(name ="branch_id")
    private int branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_address")
    private String branchAddress;
}
