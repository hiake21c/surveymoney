package com.surveymoney.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyAnswerDto {

    @NotNull
    private String answerTile;

    private int answerCount;

    private String answerCheck;
}
