package com.surveymoney.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="sv_test")
@Data
public class Tests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String description;
}
