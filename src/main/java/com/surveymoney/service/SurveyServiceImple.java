package com.surveymoney.service;

import com.surveymoney.model.*;
import com.surveymoney.repository.SurveyAnswerRepository;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.repository.SurveyQuestionRepository;
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

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    /**
     * 등록
     * @param surveyParam
     * @return
     */
    @Override
    @Transactional
    public Long surveyRegister(SurveyBaseDto surveyParam){

        SurveyBase surveyBase = setSurveyBase(surveyParam);
        List<SurveyQuestion> surveyQuestionList = new ArrayList<>();

        if(surveyParam.getQuestions() != null){

            surveyParam.getQuestions().forEach(questParam->{

                SurveyQuestion surveyQuestion = setSurveyQuestion(questParam);
                surveyQuestion.setSurveyBase(surveyBase);

                if(questParam.getAnswers() != null){
                    List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<>();

                    questParam.getAnswers().forEach(ans->{
                        SurveyAnswer surveyAnswer = setSurveyAnswer(ans);
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

    private SurveyAnswer setSurveyAnswer(SurveyAnswerDto ans) {
        SurveyAnswer surveyAnswer = new  SurveyAnswer();
        surveyAnswer.setAnswerContent(ans.getAnswerContent());
        surveyAnswer.setDisplayYn(ans.getDisplayYn());
        surveyAnswer.setUseYn(ans.getUseYn());
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }

    private SurveyQuestion setSurveyQuestion(SurveyQuestionDto questParam) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();

        surveyQuestion.setQuestionType(questParam.getQuestionType());
        surveyQuestion.setQuestionTitle(questParam.getQuestionTitle());
        surveyQuestion.setDisplayYn(questParam.getDisplayYn());
        surveyQuestion.setUseYn(questParam.getUseYn());
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
        return surveyQuestion;
    }

    private SurveyBase setSurveyBase(SurveyBaseDto surveyParam) {
        SurveyBase surveyBase = new SurveyBase();
        surveyBase.setTitle(surveyParam.getTitle());
        surveyBase.setStateType(surveyParam.getStateType());
        surveyBase.setDisplayYn(surveyParam.getDisplayYn());
        surveyBase.setUseYn(surveyParam.getUseYn());
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);
        return surveyBase;
    }

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
