package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;

import java.util.List;

public interface SurveyService  {

    Long insertSurveyInfo(SurveyBase surveyBase);

    List<SurveyBase> getSurveyBaseAll();

    SurveyBase getSurveyBase(Long id);
}
