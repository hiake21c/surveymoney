package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.repository.SurveyAnswerRepository;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.repository.SurveyQuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyDeleteServiceImpl implements  SurveyDeleteService {

    private static Logger logger = LogManager.getLogger(SurveyDeleteServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;
    /**
     * 설문조사 삭제
     * @param baseId
     */
    @Override
    public void deleteSurveyBase(Long baseId) {
        surveyBaseRepository.deleteById(baseId);
    }

    @Override
    public List<SurveyQuestion> deleteQuestion(Long baseId, Long qstId) {
        SurveyBase surveyBase = surveyBaseRepository.getOne(baseId);

        List<SurveyQuestion> surveyQuestionList = new ArrayList<>();
        surveyBase.getSurveyQuestionList().forEach(question->{
            SurveyQuestion surveyQuestion = surveyQuestionRepository.getOne(qstId);

            if(surveyQuestion != null){

            }

        });
        return surveyBase.getSurveyQuestionList();
    }
}
