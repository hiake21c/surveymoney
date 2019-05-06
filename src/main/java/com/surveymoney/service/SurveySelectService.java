package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;

import java.util.List;

public interface SurveySelectService {

    SurveyBase surveyDetail(Long id);

    List<SurveyBase> surveyAllList();

}
