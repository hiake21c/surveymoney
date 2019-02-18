package com.surveymoney.controller.rest;

import com.surveymoney.bean.Response;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/survey")
@Slf4j
public class SurveyController {

    @Autowired
    SurveyService surveyService;



    /**
     * Survey 등록
     * @param param
     * @return Response
     */

    @PostMapping(value = "/surveyRegister", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> surveyRegister(@RequestBody @Valid SurveyBaseDto param, Errors error){

        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("필수값이 누락되었습니다.");
            response.putContext("objectName",error.getFieldError().getObjectName());
            response.putContext("field",error.getFieldError().getField());
            response.putContext("errorMessage",error.getFieldError().getDefaultMessage());
           // response.putContext("error",error.getFieldError());
            return ResponseEntity.ok(response);
        }

        try{

            Long resultId =  surveyService.surveyRegister(param);
            if(resultId == null){
                response.setReturnCode(600);
                response.setReturnMessage("등록이 실패 하였습니다.");
                return ResponseEntity.ok(response);
            }

            response.putContext("id",resultId);

        }catch(Exception e){

            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Survey 상세 조회
     * @param baseId
     * @return Response
     */
    @GetMapping(value="/surveyDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> surveyDetail(@RequestParam @Valid Long baseId){

        Response response = new Response();

        if(baseId ==null){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{

            SurveyBase resultObj =  surveyService.surveyDetail(baseId);
            response.putContext("data",resultObj);
            response.putContext("resultId",resultObj.getId());

        }catch(Exception e){

            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 설문조사 목록 조회
     * @return Response
     */
    @GetMapping(value = "/surveyList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<Response> surveyList(){
        Response response = new Response();

        List<SurveyBase> resultList = surveyService.surveyAllList();

        if(resultList.size() <=0){
            response.setReturnMessage("조회한 목록이 없습니다.");
            return ResponseEntity.ok(response);
        }
        response.putContext("data",resultList);
        return ResponseEntity.ok(response);

    }

    /**
     * 설문조사를 삭제한다.
     * @param baseId
     * @return
     */
    @DeleteMapping(value="/surveyDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> surveyDelete(@RequestParam @Valid Long baseId){
        Response response = new Response();

        if(baseId ==null){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{



        }catch(Exception e){

            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }




}
