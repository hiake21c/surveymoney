package com.surveymoney.service;

import com.surveymoney.model.*;
import com.surveymoney.repository.SurveyBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SurveyServiceImple implements SurveyService {

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    /**
     * 등록
     * @param surveyParam
     * @return
     */
    @Override
    @Transactional
    public Long insertSurveyInfo(SurveyBaseDto surveyParam)throws Exception{

        SurveyBase surveyBase = getSurveyBase(surveyParam);
        List<SurveyQuestion> surveyQuestionList = new ArrayList<>();

        surveyParam.getQuestions().forEach(questParam->{

            SurveyQuestion surveyQuestion = getSurveyQuestion(questParam);
            surveyQuestion.setSurveyBase(surveyBase);

            List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<>();

            questParam.getAnswers().forEach(ans->{
                SurveyAnswer surveyAnswer = getSurveyAnswer(ans);
                surveyAnswer.setSurveyQuestion(surveyQuestion);
                surveyAnswerArrayList.add(surveyAnswer);

            });

            surveyQuestion.setSurveyAnswerList(surveyAnswerArrayList);
            surveyQuestionList.add(surveyQuestion);

        });

        surveyBase.setSurveyQuestionList(surveyQuestionList);
        SurveyBase resultObj = surveyBaseRepository.save(surveyBase);

        return resultObj.getId();
    }

    private SurveyAnswer getSurveyAnswer(SurveyAnswerDto ans) {
        SurveyAnswer surveyAnswer = new  SurveyAnswer();
        surveyAnswer.setAnswerTile(ans.getAnswerTile());
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }

    private SurveyQuestion getSurveyQuestion(SurveyQuestionDto questParam) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();

        surveyQuestion.setQuestionType(questParam.getQuestionType());
        surveyQuestion.setQuestionTitle(questParam.getQuestionTitle());
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
        return surveyQuestion;
    }

    private SurveyBase getSurveyBase(SurveyBaseDto surveyParam) {
        SurveyBase surveyBase = new SurveyBase();
        surveyBase.setTitle(surveyParam.getTitle());
        surveyBase.setState(surveyParam.getState());
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);
        return surveyBase;
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
    @Transactional
    public SurveyBase dtailSurvey(Long id) throws Exception{

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
