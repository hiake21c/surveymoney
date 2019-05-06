package com.surveymoney.service;

import com.surveymoney.model.*;
import com.surveymoney.repository.SurveyBaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyRegisterServiceImpl implements SurveyRegisterService {

    private static Logger logger = LogManager.getLogger(SurveyRegisterServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    /**
     * 등록
     * @param surveyParam
     * @return
     */
    @Override
    @Transactional
    public Long surveyRegister(SurveyBaseDto surveyParam){

        SurveyBase surveyBase = setSurveyBase(new SurveyBase(),surveyParam);
        List<SurveyQuestion> surveyQuestionList = new ArrayList<>();


        if(surveyParam.getQuestions() != null){

            surveyParam.getQuestions().forEach(questParam->{

                SurveyQuestion surveyQuestion = setSurveyQuestion(new SurveyQuestion(), questParam);
                surveyQuestion.setSurveyBase(surveyBase);

                if(questParam.getAnswers() != null){
                    List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<>();

                    questParam.getAnswers().forEach(ans->{
                        SurveyAnswer surveyAnswer = setSurveyAnswer(new SurveyAnswer(),ans);
                        surveyAnswer.setSurveyQuestion(surveyQuestion);
                        surveyAnswerArrayList.add(surveyAnswer);
                    });

                    surveyQuestion.setSurveyAnswerList(surveyAnswerArrayList);
                }

                surveyQuestionList.add(surveyQuestion);

            });
            surveyBase.setSurveyQuestionList(surveyQuestionList);
        }

        SurveyBase resultObj = surveyBaseRepository.save(surveyBase);


        return resultObj.getId();
    }

    private SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion, SurveyQuestionDto questParam) {
        surveyQuestion.setQuestionType(Optional.ofNullable(questParam.getQuestionType()).orElse(surveyQuestion.getQuestionType()));
        surveyQuestion.setQuestionTitle(Optional.ofNullable(questParam.getQuestionTitle()).orElse(surveyQuestion.getQuestionTitle()));
        surveyQuestion.setDisplayYn(Optional.ofNullable(questParam.getDisplayYn()).orElse(surveyQuestion.getDisplayYn()));
        surveyQuestion.setUseYn(Optional.ofNullable(questParam.getUseYn()).orElse(surveyQuestion.getUseYn()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
        return surveyQuestion;
    }

    private SurveyBase setSurveyBase(SurveyBase surveyBase, SurveyBaseDto surveyParam) {
        surveyBase.setTitle(Optional.ofNullable(surveyParam.getTitle()).orElse(surveyBase.getTitle()));
        surveyBase.setStateType(Optional.ofNullable(surveyParam.getStateType()).orElse(surveyBase.getStateType()));
        surveyBase.setDisplayYn(Optional.ofNullable(surveyParam.getDisplayYn()).orElse(surveyBase.getDisplayYn()));
        surveyBase.setUseYn(Optional.ofNullable(surveyParam.getUseYn()).orElse(surveyBase.getUseYn()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);

        return surveyBase;
    }

    private SurveyAnswer setSurveyAnswer(SurveyAnswer surveyAnswer, SurveyAnswerDto ans) {
        surveyAnswer.setAnswerContent(Optional.ofNullable(ans.getAnswerContent()).orElse(""));
        surveyAnswer.setDisplayYn(Optional.ofNullable(ans.getDisplayYn()).orElse(surveyAnswer.getDisplayYn()));
        surveyAnswer.setUseYn(Optional.ofNullable(ans.getUseYn()).orElse(surveyAnswer.getUseYn()));
        surveyAnswer.setAnswerCount(Optional.ofNullable(ans.getAnswerCount()).orElse(surveyAnswer.getAnswerCount()));
        surveyAnswer.setScale(Optional.ofNullable(ans.getScale()).orElse(surveyAnswer.getScale()));
        surveyAnswer.setShapeType(Optional.ofNullable(ans.getShapeType()).orElse(surveyAnswer.getShapeType()));
        surveyAnswer.setAnswerCheck(Optional.ofNullable(ans.getAnswerCheck()).orElse(surveyAnswer.getAnswerCheck()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }


}
