package com.surveymoney.model;

import com.surveymoney.enumulation.AnswerShapeType;
import com.surveymoney.enumulation.YesNoType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SurveyAnswerDto {

    @NotNull
    private String answerContent;

    private int answerCount;

    private String answerCheck;

    private AnswerShapeType shapeType;

    private  int scale;

    private YesNoType useYn;

    private YesNoType displayYn;
}
