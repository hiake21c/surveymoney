package com.surveymoney.service;

import com.surveymoney.enumulation.YesNoType;
import com.surveymoney.model.*;
import com.surveymoney.repository.SurveyAnswerRepository;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.repository.SurveyQuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyUpdateServiceImpl  implements  SurveyUpdateService{

    private static Logger logger = LogManager.getLogger(SurveyUpdateServiceImpl.class);

    @Autowired
    SurveyBaseRepository surveyBaseRepository;

    @Autowired
    SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    /**
     * 질문 수정, 답변 수정
     * @param baseId
     * @param param
     */
    @Override
    public void updateQuestion(Long baseId, List<SurveyQuestionDto> param) {

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
     * 설문 정보 수정
     * @param param
     * @return
     */
    @Override
    public SurveyBase updateSurvey(SurveyBaseDto param) {
        SurveyBase surveyBase = surveyBaseRepository.getOne(param.getId());
        //SurveyBase setSurveyBase =setSurveyBase(surveyBase, param);
        surveyBaseRepository.save(setSurveyBase(surveyBase, param));
        return surveyBase;
    }

    /**
     * 설문지 사용여부 수정
     * @param baseId
     * @param useYn
     * @return
     */
    @Override
    public SurveyBase updateBaseUseYn(Long baseId, String useYn) {

        SurveyBase surveyBase = surveyBaseRepository.getOne(baseId);
        surveyBase.setUseYn(YesNoType.valueOf(useYn));
        surveyBaseRepository.save(surveyBase);

        return surveyBase;
    }

    /**
     * 질문 사용여부 수정
     * @param qstId
     * @param useYn
     * @return
     */
    @Override
    public void updateQusetionUseYn(Long qstId, String useYn) {
        SurveyQuestion surveyQuestion = surveyQuestionRepository.getOne(qstId);
        surveyQuestion.setUseYn(YesNoType.valueOf(useYn));
        surveyQuestionRepository.save(surveyQuestion);

    }

    private SurveyQuestion setSurveyQuestion(SurveyQuestion surveyQuestion, SurveyQuestionDto questParam) {
        surveyQuestion.setQuestionType(Optional.ofNullable(questParam.getQuestionType()).orElse(surveyQuestion.getQuestionType()));
        surveyQuestion.setQuestionTitle(Optional.ofNullable(questParam.getQuestionTitle()).orElse(surveyQuestion.getQuestionTitle()));
        surveyQuestion.setDisplayYn(Optional.ofNullable(questParam.getDisplayYn()).orElse(surveyQuestion.getDisplayYn()));
        surveyQuestion.setUseYn(Optional.ofNullable(questParam.getUseYn()).orElse(surveyQuestion.getUseYn()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
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

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        surveyBase.setCreateId(1L);
        surveyBase.setCreateDate(LocalDateTime.now());
        surveyBase.setModifyDate(LocalDateTime.now());
        surveyBase.setModifyId(1L);

        return surveyBase;
    }

    private SurveyAnswer setSurveyAnswer(SurveyAnswer surveyAnswer, SurveyAnswerDto ans) {
        surveyAnswer.setAnswerContent(Optional.ofNullable(ans.getAnswerContent()).orElse(""));
        surveyAnswer.setDisplayYn(Optional.ofNullable(ans.getDisplayYn()).orElse(surveyAnswer.getDisplayYn()));
        surveyAnswer.setUseYn(Optional.ofNullable(ans.getUseYn()).orElse(surveyAnswer.getUseYn()));
        surveyAnswer.setAnswerCount(Optional.ofNullable(ans.getAnswerCount()).orElse(surveyAnswer.getAnswerCount()));
        surveyAnswer.setScale(Optional.ofNullable(ans.getScale()).orElse(surveyAnswer.getScale()));
        surveyAnswer.setShapeType(Optional.ofNullable(ans.getShapeType()).orElse(surveyAnswer.getShapeType()));
        surveyAnswer.setAnswerCheck(Optional.ofNullable(ans.getAnswerCheck()).orElse(surveyAnswer.getAnswerCheck()));

        //TODO : 수정, 등록 정보 공통으로 해야 함 AuditingEntityListener로 구현 할꺼임.
        surveyAnswer.setCreateId(1L);
        surveyAnswer.setCreateDate(LocalDateTime.now());
        surveyAnswer.setModifyDate(LocalDateTime.now());
        surveyAnswer.setModifyId(1L);
        return surveyAnswer;
    }
}
