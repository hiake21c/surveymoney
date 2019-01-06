package com.surveymoney.model;

import com.surveymoney.enumulation.SurveyState;

import java.util.List;

public class SurveyBase {

    private Long id;

    private String title;

    private SurveyState state;

    private List<SurveyQuestion> surveyQuestionList;

}
