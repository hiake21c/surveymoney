package com.surveymoney.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TestDto {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
