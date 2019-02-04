package com.surveymoney.service;

import com.surveymoney.model.*;
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
    public Long insertSurveyInfo(SurveyBaseDto surveyBase){

        SurveyBase base = new SurveyBase();
        base.setTitle(surveyBase.getTitle());
        base.setState(surveyBase.getState());
        base.setCreateId(1L);
        base.setCreateDate(LocalDateTime.now());
        base.setModifyDate(LocalDateTime.now());
        base.setModifyId(1L);


        /****************************************************************
         * Question에 셋팅
         ****************************************************************/
        List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();

        for(int i=0; i<surveyBase.getQuestions().size(); i++){
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            SurveyQuestionDto param = surveyBase.getQuestions().get(i);

            surveyQuestion.setQuestionType(param.getQuestionType());
            surveyQuestion.setQuestionTitle(param.getQuestionTitle());
            surveyQuestion.setCreateId(1L);
            surveyQuestion.setCreateDate(LocalDateTime.now());
            surveyQuestion.setModifyDate(LocalDateTime.now());
            surveyQuestion.setModifyId(1L);

            /****************************************************************
             * Question에 SurveyBase 참조설정
             ****************************************************************/
            surveyQuestion.setSurveyBase(base);

            /****************************************************************
             * Answer에 셋팅
             ****************************************************************/
            List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<SurveyAnswer>();

            for(int j=0; j< param.getAnswers().size(); j++){
                SurveyAnswer surveyAnswer = new  SurveyAnswer();
                SurveyAnswerDto answerParam = param.getAnswers().get(i);

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
        base.setSurveyQuestionList(surveyQuestionList);

        /****************************************************************
         * SurveyBase 영속성
         ****************************************************************/

        SurveyBase resultObj = surveyBaseRepository.save(base);

        return resultObj.getId();
    }

    /**
     * 모든 설문 조사 조회
     * @return
     */
    @Override
    public List<SurveyBase> getSurveyBaseAll(){

        List<SurveyBase> baseList = surveyBaseRepository.findAll();
        return baseList;
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    @Override
    public SurveyBase getSurveyBase(Long id){

        SurveyBase base = surveyBaseRepository.getOne(id);
        return base;
    }

    /**
     * 설문조사 삭제
     * @param baseId
     */
    @Override
    public void deleteSurveyBase(Long baseId) {

        surveyBaseRepository.deleteById(baseId);
    }


}
