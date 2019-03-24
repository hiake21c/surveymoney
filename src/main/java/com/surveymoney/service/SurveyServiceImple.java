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
import java.util.Optional;

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

        SurveyBase surveyBase = setSurveyBase(new SurveyBase(),surveyParam);
        List<SurveyQuestion> surveyQuestionList = new ArrayList<>();


        if(surveyParam.getQuestions() != null){

            surveyParam.getQuestions().forEach(questParam->{

                SurveyQuestion surveyQuestion = setSurveyQuestion(new SurveyQuestion(), questParam);
                surveyQuestion.setSurveyBase(surveyBase);

                if(questParam.getAnswers() != null){
                    List<SurveyAnswer> surveyAnswerArrayList = new ArrayList<>();

                    questParam.getAnswers().forEach(ans->{
                        SurveyAnswer surveyAnswer = setSurveyAnswer(new SurveyAnswer(),ans);
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
     * 설문 정보 수정
     * @param param
     * @return
     */
    @Override
    public void updateSurvey(SurveyBaseDto param) {
        SurveyBase surveyBase = surveyBaseRepository.getOne(param.getId());
        //SurveyBase setSurveyBase =setSurveyBase(surveyBase, param);
        surveyBaseRepository.save(setSurveyBase(surveyBase, param));
    }

    @Override
    public void updateQuestion(Long baseId,List<SurveyQuestionDto> param) {

        Optional<SurveyBase> base = surveyBaseRepository.findById(baseId);
        base.ifPresent( baseDetail ->{

            Optional<List<SurveyQuestionDto>> paramQuestion = Optional.ofNullable(param);

            paramQuestion.ifPresent( detailQuestion->{

                List<SurveyQuestion> editQuestion = new ArrayList<>();
                detailQuestion.forEach( questionDto->{
                    SurveyQuestion question = surveyQuestionRepository.getOne(questionDto.getId());

//                    Optional<List<SurveyAnswerDto>> paramAnswer = Optional.ofNullable(questionDto.getAnswers());
//                    paramAnswer.ifPresent( detailAnswer->{
//
//                        List<SurveyAnswer> editAnswer = new ArrayList<>();
//                        detailAnswer.forEach( answerDto->{
//
//                            SurveyAnswer answer = surveyAnswerRepository.getOne(answerDto.getId());
//
//                            surveyAnswerRepository.save( setSurveyAnswer(answer,answerDto));
//                            editAnswer.add(answer);
//
//                        });
//                        question.setSurveyAnswerList(editAnswer);
//                    });


                    surveyQuestionRepository.save(setSurveyQuestion(question, questionDto));
                    editQuestion.add(question);
                });

                baseDetail.setSurveyQuestionList(editQuestion);
            });

        });

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

    private SurveyAnswer setSurveyAnswer(SurveyAnswer surveyAnswer, SurveyAnswerDto ans) {
        surveyAnswer.setAnswerContent(ans.getAnswerContent());
        surveyAnswer.setDisplayYn(ans.getDisplayYn());
        surveyAnswer.setUseYn(ans.getUseYn());
        surveyAnswer.setAnswerCount(ans.getAnswerCount());
        surveyAnswer.setScale(ans.getScale());
        surveyAnswer.setShapeType(ans.getShapeType());
        surveyAnswer.setAnswerCheck(ans.getAnswerCheck());
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }

    private SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion,SurveyQuestionDto questParam) {
        surveyQuestion.setQuestionType(questParam.getQuestionType());
        surveyQuestion.setQuestionTitle(questParam.getQuestionTitle());
        surveyQuestion.setDisplayYn(questParam.getDisplayYn());
        surveyQuestion.setUseYn(questParam.getUseYn());
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
        return surveyQuestion;
    }

    private SurveyBase setSurveyBase(SurveyBase surveyBase, SurveyBaseDto surveyParam) {
        surveyBase.setTitle(surveyParam.getTitle());
        surveyBase.setStateType(surveyParam.getStateType());
        surveyBase.setDisplayYn(surveyParam.getDisplayYn());
        surveyBase.setUseYn(surveyParam.getUseYn());
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);
        return surveyBase;
    }




}
