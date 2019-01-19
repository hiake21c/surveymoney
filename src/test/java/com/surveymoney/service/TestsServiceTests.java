package com.surveymoney.service;

import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswer;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyQuestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsServiceTests {

    @Autowired
    SurveyService surveyService;


    @Test
    public void insertTest(){

        SurveyBase surveyBase = new SurveyBase();
        surveyBase.setState(SurveyState.DRAFT);
        surveyBase.setTitle("test 설문조사");
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);

        List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
        for(int i=0; i<2; i++) {
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            if (i == 0) surveyQuestion.setQuestionTitle("Test를 해봤습니까?");
            else surveyQuestion.setQuestionTitle("우리나라가 강산입니까?");
            surveyQuestion.setQuestionType(QuestionType.SINGLE);
            surveyQuestion.setCreateId(1L);
            surveyQuestion.setCreateDate(LocalDateTime.now());
            surveyQuestion.setModifyDate(LocalDateTime.now());
            surveyQuestion.setModifyId(1L);

            surveyQuestionList.add(surveyQuestion);
        }
        surveyBase.setQuestions(surveyQuestionList);


        List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<SurveyAnswer>();
        for(int j=0; j<2; j++){
            SurveyAnswer surveyAnswer = new  SurveyAnswer();
            if(j==0) surveyAnswer.setAnswerTile("예");
            else surveyAnswer.setAnswerTile("아니오");
            surveyAnswer.setCreateId(1L);
            surveyAnswer.setCreateDate(LocalDateTime.now());
            surveyAnswer.setModifyDate(LocalDateTime.now());
            surveyAnswer.setModifyId(1L);

            surveyAnswerArrayList.add(surveyAnswer);
        }
        surveyBase.setAnswers(surveyAnswerArrayList);

        SurveyBase returnBase = surveyService.insertSurveyInfo(surveyBase);

        assertNotNull("NOT NULL Exception !!",returnBase.getId());
        System.out.println("======> Insert Id : "+returnBase.getId());

    }
}
