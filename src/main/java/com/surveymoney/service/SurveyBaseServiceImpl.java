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
public class SurveyBaseServiceImpl implements SurveyBaseService {

    private static Logger logger = LogManager.getLogger(SurveyBaseServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionService surveyQuestionService;

    @Autowired
    SurveyAnswerService surveyAnswerService;

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

                SurveyQuestion surveyQuestion = surveyQuestionService.setSurveyQuestion(new SurveyQuestion(), questParam);
                surveyQuestion.setSurveyBase(surveyBase);

                if(questParam.getAnswers() != null){
                    List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<>();

                    questParam.getAnswers().forEach(ans->{
                        SurveyAnswer surveyAnswer = surveyAnswerService.setSurveyAnswer(new SurveyAnswer(),ans);
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

    @Override
    public SurveyBase setSurveyBase(SurveyBase surveyBase, SurveyBaseDto surveyParam) {
        surveyBase.setTitle(Optional.ofNullable(surveyParam.getTitle()).orElse(surveyBase.getTitle()));
        surveyBase.setStateType(Optional.ofNullable(surveyParam.getStateType()).orElse(surveyBase.getStateType()));
        surveyBase.setDisplayYn(Optional.ofNullable(surveyParam.getDisplayYn()).orElse(surveyBase.getDisplayYn()));
        surveyBase.setUseYn(Optional.ofNullable(surveyParam.getUseYn()).orElse(surveyBase.getUseYn()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        setCommonBase(surveyBase);

        return surveyBase;
    }

    private void setCommonBase(SurveyBase surveyBase) {
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);
    }

}
