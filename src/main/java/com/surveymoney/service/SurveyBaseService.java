package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;

public interface SurveyBaseService {

    Long surveyRegister(SurveyBaseDto surveyBase);

    SurveyBase setSurveyBase(SurveyBase surveyBase, SurveyBaseDto surveyParam);
}
