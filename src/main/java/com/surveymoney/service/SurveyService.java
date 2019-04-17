package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.model.SurveyQuestionDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SurveyService  {

    Long surveyRegister(SurveyBaseDto surveyBase);

    SurveyBase surveyDetail(Long id);

    List<SurveyBase> surveyAllList();

    void updateQuestion(Long baseId, List<SurveyQuestionDto> param);

    void deleteSurveyBase(Long baseId);

    List<SurveyQuestion> deleteQuestion(Long baseId, Long qstId);

    SurveyBase updateSurvey(SurveyBaseDto param);

    SurveyBase updateBaseUseYn(Long baseId, String useYn);

    void updateQusetionUseYn(Long qstId, String useYn);

    SurveyBase BaseSearch(Specification<SurveyBase> search);

}
