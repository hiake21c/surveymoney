package com.surveymoney.service;

import com.surveymoney.model.SurveyQuestion;

import java.util.List;

public interface SurveyDeleteService {

    void deleteSurveyBase(Long baseId);

    List<SurveyQuestion> deleteQuestion(Long baseId, Long qstId);

}
