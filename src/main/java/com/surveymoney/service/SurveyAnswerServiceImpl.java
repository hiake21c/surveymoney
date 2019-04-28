package com.surveymoney.service;

import com.surveymoney.model.SurveyAnswer;
import com.surveymoney.model.SurveyAnswerDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService{

    @Override
    public SurveyAnswer setSurveyAnswer(SurveyAnswer surveyAnswer, SurveyAnswerDto ans) {
        surveyAnswer.setAnswerContent(Optional.ofNullable(ans.getAnswerContent()).orElse(""));
        surveyAnswer.setDisplayYn(Optional.ofNullable(ans.getDisplayYn()).orElse(surveyAnswer.getDisplayYn()));
        surveyAnswer.setUseYn(Optional.ofNullable(ans.getUseYn()).orElse(surveyAnswer.getUseYn()));
        surveyAnswer.setAnswerCount(Optional.ofNullable(ans.getAnswerCount()).orElse(surveyAnswer.getAnswerCount()));
        surveyAnswer.setScale(Optional.ofNullable(ans.getScale()).orElse(surveyAnswer.getScale()));
        surveyAnswer.setShapeType(Optional.ofNullable(ans.getShapeType()).orElse(surveyAnswer.getShapeType()));
        surveyAnswer.setAnswerCheck(Optional.ofNullable(ans.getAnswerCheck()).orElse(surveyAnswer.getAnswerCheck()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        setCommonAnswer(surveyAnswer);
        return surveyAnswer;
    }

    private void setCommonAnswer(SurveyAnswer surveyAnswer) {
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
    }
}
