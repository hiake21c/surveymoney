package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import com.surveymoney.repository.SurveyAnswerRepository;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.repository.SurveyQuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveySearchServiceImpl implements SurveySearchService {

    private static Logger logger = LogManager.getLogger(SurveySearchServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    /**
     * 질문 검색
     * @return
     */
    @Override
    public List<SurveyBase> baseSearch(Specification<SurveyBase> search) {

        List<SurveyBase> title = surveyBaseRepository.findAll(search);
        return title;
    }
}
