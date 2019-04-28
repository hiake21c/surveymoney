package com.surveymoney.service;

import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.model.SurveyQuestionDto;

public interface SurveyQuestionService {

    SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion, SurveyQuestionDto questParam);
}
