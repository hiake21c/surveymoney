package com.surveymoney.controller.rest;

import com.surveymoney.bean.Response;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/survey/")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    /**
     * Survey 등록
     * @param survey
     * @return
     */
    @PostMapping(name = "putSurvey", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> putSurvey(@ModelAttribute @Valid SurveyBaseDto survey, Errors error)throws Exception{

        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("필수값이 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{

            Long resultObj =  surveyService.insertSurveyInfo(survey);
            if(resultObj == null){
                response.setReturnCode(600);
                response.setReturnMessage("등록이 실패 하였습니다.");
            }
        }catch(Exception e){

            response.setReturnCode(700);
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Survey 모두 조회
     * @return
     */
    @GetMapping(name = "surveyList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> getSurveyList(){

        Response response = new Response();
        List<SurveyBase> resultObj =  surveyService.getSurveyBaseAll();
        response.putContext("data",resultObj);

        return ResponseEntity.ok(response);
    }

    /**
     * Survey 상세 조회
     * @param baseId
     * @return
     */
    @GetMapping(name="surveyDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> getSurveyDetail(@RequestParam @NotNull Long baseId, Errors error){

        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터가 존재하지 않습니다.");
            return ResponseEntity.ok(response);
        }

        SurveyBase resultObj =  surveyService.getSurveyBase(baseId);
        response.putContext("data",resultObj);
        return ResponseEntity.ok(response);
    }

    /**
     * 설문조사 삭제
     * @param baseId
     * @param error
     * @return
     */
    @DeleteMapping(name="surveyDelete/{baseId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> deleteSurvey(@PathVariable @NotNull Long baseId, Errors error)throws Exception{
        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터가 존재하지 않습니다.");
            return ResponseEntity.ok(response);
        }

        try{
            surveyService.deleteSurveyBase(baseId);
        }catch(Exception e){
            response.setReturnCode(700);
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }


}
