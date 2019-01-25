package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;

import java.util.List;

public interface SurveyService  {

    Long insertSurveyInfo(SurveyBaseDto surveyBase);

    List<SurveyBase> getSurveyBaseAll();

    SurveyBase getSurveyBase(Long id);
}
