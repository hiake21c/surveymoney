package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestionDto;

import java.util.List;

public interface SurveyUpdateService {

    void updateQuestion(Long baseId, List<SurveyQuestionDto> param);

    SurveyBase updateSurvey(SurveyBaseDto param);

    SurveyBase updateBaseUseYn(Long baseId, String useYn);

    void updateQusetionUseYn(Long qstId, String useYn);

}
