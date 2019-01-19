package com.surveymoney.service;

import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswer;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.repository.SurveyBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SurveyServiceImple implements SurveyService {

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    /**
     * 등록
     * @param surveyBase
     * @return
     */
    @Override
    public SurveyBase insertSurveyInfo(SurveyBase surveyBase){

        /****************************************************************
         * Question에 셋팅
         ****************************************************************/
        List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();

        for(int i=0; i<surveyBase.getQuestions().size(); i++){
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            SurveyQuestion param = surveyBase.getQuestions().get(i);

            surveyQuestion.setQuestionType(param.getQuestionType());
            surveyQuestion.setQuestionTitle(param.getQuestionTitle());
            surveyQuestion.setCreateId(1L);
            surveyQuestion.setCreateDate(LocalDateTime.now());
            surveyQuestion.setModifyDate(LocalDateTime.now());
            surveyQuestion.setModifyId(1L);

            /****************************************************************
             * Question에 SurveyBase 참조설정
             ****************************************************************/
            surveyQuestion.setSurveyBase(surveyBase);

            /****************************************************************
             * Answer에 셋팅
             ****************************************************************/
            List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<SurveyAnswer>();

            for(int j=0; j< surveyBase.getAnswers().size(); j++){
                SurveyAnswer surveyAnswer = new  SurveyAnswer();
                SurveyAnswer answerParam = surveyBase.getAnswers().get(i);

                surveyAnswer.setAnswerTile(answerParam.getAnswerTile());
                surveyAnswer.setCreateId(1L);
                surveyAnswer.setCreateDate(LocalDateTime.now());
                surveyAnswer.setModifyDate(LocalDateTime.now());
                surveyAnswer.setModifyId(1L);

                /****************************************************************
                 * Answer에 SurveyQuestion 참조설정
                 ****************************************************************/
                surveyAnswer.setSurveyQuestion(surveyQuestion);

                surveyAnswerArrayList.add(surveyAnswer);
            }

            /****************************************************************
             * Question에 SurveyAnswer Set
             ****************************************************************/
            surveyQuestion.setSurveyAnswerList(surveyAnswerArrayList);

            surveyQuestionList.add(surveyQuestion);
        }

        /****************************************************************
         * Base에 Question목록 set
         ****************************************************************/
        surveyBase.setSurveyQuestionList(surveyQuestionList);

        /****************************************************************
         * SurveyBase 영속성
         ****************************************************************/
        SurveyBase returnBase = surveyBaseRepository.save(surveyBase);

        return surveyBase;
    }


}
