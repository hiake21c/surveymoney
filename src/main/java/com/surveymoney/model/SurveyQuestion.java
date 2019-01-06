package com.surveymoney.model;

import com.surveymoney.enumulation.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SurveyQuestion extends BaseModel{

    private Long id;

    private String questionTitle;

    private QuestionType questionType;

    private SurveyBase surveyBase;

    private List<SurveyAnswer> surveyAnswerList;

}
