package com.surveymoney.model;

public class SurveyAnswer extends BaseModel {

    private Long id;

    private String answerTile;

    private Long answerCount;

    private String answerCheck;

    private SurveyQuestion surveyQuestion;
}
