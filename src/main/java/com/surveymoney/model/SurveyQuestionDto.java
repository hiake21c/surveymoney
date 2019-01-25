package com.surveymoney.model;

import com.surveymoney.enumulation.QuestionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SurveyQuestionDto {

    @NotNull
    private String questionTitle;

    @NotNull
    private QuestionType questionType;

    @NotNull
    private List<SurveyAnswerDto> answers;
}
