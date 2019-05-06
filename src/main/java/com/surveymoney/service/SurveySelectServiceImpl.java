package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.repository.SurveyAnswerRepository;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.repository.SurveyQuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SurveySelectServiceImpl implements SurveySelectService {

    private static Logger logger = LogManager.getLogger(SurveySelectServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    /**
     * 상세 조회
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SurveyBase surveyDetail(Long id){

        SurveyBase base = surveyBaseRepository.getOne(id);
        return base;
    }

    /**
     * 모든 설문 조사 조회
     * @return
     */
    @Override
    public List<SurveyBase> surveyAllList(){

        List<SurveyBase> baseList = surveyBaseRepository.findAll();
        return baseList;
    }
}
