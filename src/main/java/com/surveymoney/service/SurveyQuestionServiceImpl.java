package com.surveymoney.service;

import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.model.SurveyQuestionDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService{

    @Override
    public SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion, SurveyQuestionDto questParam) {
        surveyQuestion.setQuestionType(Optional.ofNullable(questParam.getQuestionType()).orElse(surveyQuestion.getQuestionType()));
        surveyQuestion.setQuestionTitle(Optional.ofNullable(questParam.getQuestionTitle()).orElse(surveyQuestion.getQuestionTitle()));
        surveyQuestion.setDisplayYn(Optional.ofNullable(questParam.getDisplayYn()).orElse(surveyQuestion.getDisplayYn()));
        surveyQuestion.setUseYn(Optional.ofNullable(questParam.getUseYn()).orElse(surveyQuestion.getUseYn()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        setCommonQuestion(surveyQuestion);
        return surveyQuestion;
    }

    private void setCommonQuestion(SurveyQuestion surveyQuestion) {
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
    }
}
