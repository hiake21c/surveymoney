package com.surveymoney.service;

import com.surveymoney.enumulation.YesNoType;
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
    public SurveyBase updateBaseUseYn(Long baseId, String useYn) {

        SurveyBase surveyBase = surveyBaseRepository.getOne(baseId);

        surveyBase.setUseYn(YesNoType.valueOf(useYn));
        surveyBaseRepository.save(surveyBase);

        return surveyBase;
    }

    /**
     * 질문 수정, 답변 수정
     * @param baseId
     * @param param
     */
    @Override
    public void updateQuestion(Long baseId,List<SurveyQuestionDto> param) {

        Optional<List<SurveyQuestionDto>> optionParam = Optional.ofNullable(param);
        optionParam.ifPresent( paramQuestion-> {

            //설문 기본 정보를 조회해 온다.
            Optional<SurveyBase> optionBase = surveyBaseRepository.findById(baseId);
            optionBase.ifPresent(baseDetail -> {

                //질문정보에 변경된 정보를 영속성 시킨다.
                List<SurveyQuestion> editQuestion = new ArrayList<>();
                paramQuestion.forEach(questionDto -> {
                    SurveyQuestion question = surveyQuestionRepository.getOne(questionDto.getId());

                    Optional<List<SurveyAnswerDto>> optionAnswer = Optional.ofNullable(questionDto.getAnswers());
                    optionAnswer.ifPresent(detailAnswer -> {

                        List<SurveyAnswer> editAnswer = new ArrayList<>();
                        detailAnswer.forEach(answerDto -> {

                            SurveyAnswer result2 = question.getSurveyAnswerList().stream()
                                    .filter(x -> x.getId() == answerDto.getId())
                                    .findAny()
                                    .orElse(null);
                            editAnswer.add(setSurveyAnswer(result2,answerDto));

                        });
                        question.setSurveyAnswerList(editAnswer);
                    });
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
        surveyAnswer.setAnswerContent(Optional.ofNullable(ans.getAnswerContent()).orElse(""));
        surveyAnswer.setDisplayYn(Optional.ofNullable(ans.getDisplayYn()).orElse(surveyAnswer.getDisplayYn()));
        surveyAnswer.setUseYn(Optional.ofNullable(ans.getUseYn()).orElse(surveyAnswer.getUseYn()));
        surveyAnswer.setAnswerCount(Optional.ofNullable(ans.getAnswerCount()).orElse(surveyAnswer.getAnswerCount()));
        surveyAnswer.setScale(Optional.ofNullable(ans.getScale()).orElse(surveyAnswer.getScale()));
        surveyAnswer.setShapeType(Optional.ofNullable(ans.getShapeType()).orElse(surveyAnswer.getShapeType()));
        surveyAnswer.setAnswerCheck(Optional.ofNullable(ans.getAnswerCheck()).orElse(surveyAnswer.getAnswerCheck()));
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }

    private SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion,SurveyQuestionDto questParam) {
        surveyQuestion.setQuestionType(Optional.ofNullable(questParam.getQuestionType()).orElse(surveyQuestion.getQuestionType()));
        surveyQuestion.setQuestionTitle(Optional.ofNullable(questParam.getQuestionTitle()).orElse(surveyQuestion.getQuestionTitle()));
        surveyQuestion.setDisplayYn(Optional.ofNullable(questParam.getDisplayYn()).orElse(surveyQuestion.getDisplayYn()));
        surveyQuestion.setUseYn(Optional.ofNullable(questParam.getUseYn()).orElse(surveyQuestion.getUseYn()));
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyQuestion.setCreateId(1L);
        surveyQuestion.setCreateDate(LocalDateTime.now());
        surveyQuestion.setModifyDate(LocalDateTime.now());
        surveyQuestion.setModifyId(1L);
        return surveyQuestion;
    }

    private SurveyBase setSurveyBase(SurveyBase surveyBase, SurveyBaseDto surveyParam) {
        surveyBase.setTitle(Optional.ofNullable(surveyParam.getTitle()).orElse(surveyBase.getTitle()));
        surveyBase.setStateType(Optional.ofNullable(surveyParam.getStateType()).orElse(surveyBase.getStateType()));
        surveyBase.setDisplayYn(Optional.ofNullable(surveyParam.getDisplayYn()).orElse(surveyBase.getDisplayYn()));
        surveyBase.setUseYn(Optional.ofNullable(surveyParam.getUseYn()).orElse(surveyBase.getUseYn()));
        //TODO : 수정, 등록 정보 공통으로 해야 함. 수정, 등록일떄 다이나믹하게 값을 셋팅을 해야 함.
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);
        return surveyBase;
    }




}
