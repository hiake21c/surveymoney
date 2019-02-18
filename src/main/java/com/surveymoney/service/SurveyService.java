package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;

import java.util.List;

public interface SurveyService  {

    Long surveyRegister(SurveyBaseDto surveyBase);

    SurveyBase surveyDetail(Long id);

    List<SurveyBase> surveyAllList();

    void deleteSurveyBase(Long baseId);
}
