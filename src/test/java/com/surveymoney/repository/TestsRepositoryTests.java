package com.surveymoney.repository;

import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswer;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyQuestion;
import com.surveymoney.model.Tests;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestsRepositoryTests {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TestRepository testRepository;

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    @Test
    public void testJPAInsert(){

        Tests test = new Tests();
        test.setId(1L);
        test.setName("park min jung");
        test.setDescription("JPA TEST");

        testRepository.save(test);

        //assertNull("NULL Exception !!",testRepository.getOne(1L));
        assertNotNull("NOT NULL Exception !!",testRepository.getOne(1L));

        System.out.println(testRepository.getOne(1L));
    }

    @Test
    public void testSurveyInsert(){

        SurveyBase surveyBase = new SurveyBase();
        LocalDateTime date = LocalDateTime.now();

        //Survey에 셋팅
        surveyBase.setState(SurveyState.DRAFT);
        surveyBase.setTitle("test 설문조사");
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(date);
        surveyBase.setModifyDate(date);
        surveyBase.setModifyId(1L);

        //Question에 셋팅
        List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
        for(int i=0; i<2; i++){
            SurveyQuestion surveyQuestion = new SurveyQuestion();
            if(i==0) surveyQuestion.setQuestionTitle("Test를 해봤습니까?");
            else surveyQuestion.setQuestionTitle("우리나라가 강산입니까?");
            surveyQuestion.setQuestionType(QuestionType.SINGLE);
            surveyQuestion.setCreateId(1L);
            surveyQuestion.setCreateDate(date);
            surveyQuestion.setModifyDate(date);
            surveyQuestion.setModifyId(1L);

            //question에 set
            surveyQuestion.setSurveyBase(surveyBase);

            List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<SurveyAnswer>();
            for(int j=0; j<2; j++){
                SurveyAnswer surveyAnswer = new  SurveyAnswer();
                if(j==0) surveyAnswer.setAnswerTile("예");
                else surveyAnswer.setAnswerTile("아니오");
                surveyAnswer.setCreateId(1L);
                surveyAnswer.setCreateDate(date);
                surveyAnswer.setModifyDate(date);
                surveyAnswer.setModifyId(1L);

                //Answer에 set
                surveyAnswer.setSurveyQuestion(surveyQuestion);

                surveyAnswerArrayList.add(surveyAnswer);
            }

            //Question에 set
            surveyQuestion.setSurveyAnswerList(surveyAnswerArrayList);

            surveyQuestionList.add(surveyQuestion);
        }

        //base에 set
        surveyBase.setSurveyQuestionList(surveyQuestionList);

        //최종적으로 save
        SurveyBase returnBase = surveyBaseRepository.save(surveyBase);

        //System.out.println(surveyBaseRepository.getOne(1L));
        assertNotNull("NOT NULL Exception !!",surveyBaseRepository.getOne(1L));
        assertNotNull("NOT NULL Exception !!",surveyQuestionRepository.getOne(1L));
        assertNotNull("NOT NULL Exception !!",surveyAnswerRepository.getOne(1L));

    }
}
