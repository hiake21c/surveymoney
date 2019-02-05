package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;

import java.util.List;

public interface SurveyService  {

    Long insertSurveyInfo(SurveyBaseDto surveyBase)throws Exception;

    List<SurveyBase> getSurveyBaseAll();

    SurveyBase dtailSurvey(Long id) throws Exception;

    void deleteSurveyBase(Long baseId);
}
