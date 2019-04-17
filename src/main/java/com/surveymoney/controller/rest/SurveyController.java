package com.surveymoney.controller.rest;

import com.surveymoney.bean.Response;
import com.surveymoney.model.*;
import com.surveymoney.service.SurveyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController{

    private static Logger logger = LogManager.getLogger(SurveyController.class);

    @Autowired
    SurveyService surveyService;

    /**
     * Survey 신규등록
     * @param param
     * @return Response
     */

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(value="/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> getSurveyDetail(@RequestParam @Valid Long baseId){

        Response response = new Response();

        if(baseId ==null){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{

            SurveyBase resultObj =  surveyService.surveyDetail(baseId);
            response.putContext("data",resultObj);

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
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<Response> getSurveyList(){
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
     * 설문조사 수정
     * @param param
     * @param error
     * @return
     */
    @PutMapping(value = "/update/base",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> putUpdate(@RequestBody @Valid SurveyBaseDto param, Errors error){
        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("필수값이 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{
            SurveyBase surveyBase = surveyService.updateSurvey(param);
            response.putContext("data",surveyBase);
        }catch(Exception e){
            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 질문 수정
     * @param param
     * @param baseId
     * @param errors
     * @return
     */
    @PutMapping(value="/update/{baseId}/question", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> putQuestion(@RequestBody @Valid List<SurveyQuestionDto> param
            , @PathVariable Long baseId,  Errors errors){
        Response response = new Response();

        if(errors.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("필수값이 누락되었습니다. ");
            return ResponseEntity.ok(response);
        }

        if(baseId == null){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터값이 누락되었습니다. ");
            return ResponseEntity.ok(response);
        }

        try{
            surveyService.updateQuestion(baseId,param);
        }catch (Exception e){
            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }
        return ResponseEntity.ok(response);
    }




    /**
     * 설문조사를 삭제한다.
     * @param baseId
     * @return
     */
    @DeleteMapping(value="/baseDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> deleteBase(@RequestParam @Valid Long baseId){
        Response response = new Response();

        if(baseId ==null){
            response.setReturnCode(300);
            response.setReturnMessage("파라미터 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{
            surveyService.deleteSurveyBase(baseId);

        }catch(Exception e){

            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 질문을 삭제한다.
     * @param baseId
     * @param qstId
     * @return
     */
    @DeleteMapping(value = "/questionDelete/{baseId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> deleteQuestion(@PathVariable@Valid Long baseId, @RequestParam @Valid Long qstId){
        Response response = new Response();

        if(baseId == null){
            response.setReturnCode(300);
            response.setReturnMessage("설문조사 ID가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        if(qstId == null){
            response.setReturnCode(300);
            response.setReturnMessage("질문 ID가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{
            List<SurveyQuestion> result =  surveyService.deleteQuestion(baseId, qstId);
            response.putContext("data",result);
        }catch (Exception e){
            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 설문 사용여부 수정
     * @param baseId
     * @param useYn
     * @return
     */
    @PostMapping(value = "/base/{baseId}/useYn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> updateBaseUseYn( @PathVariable Long baseId,
                                                    @RequestParam String useYn){
        Response response = new Response();

        if(useYn == null){
            response.setReturnCode(300);
            response.setReturnMessage("필수 useYn 파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        if(baseId == null){
            response.setReturnCode(300);
            response.setReturnMessage("필수 baseId 파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{

            SurveyBase surveyBase = surveyService.updateBaseUseYn(baseId, useYn);
            response.putContext("data",surveyBase);
        }catch (Exception e){
            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");

        }

        return ResponseEntity.ok(response);
    }

    /**
     * 질문 사용여부를 수정한다.
     * @param qstId
     * @param useYn
     * @return
     */
    @PostMapping(value = "/question/{qstId}/useYn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> updateQuestionUseYn( @PathVariable Long qstId,
                                                     @RequestParam String useYn){
        Response response = new Response();

        if(useYn == null){
            response.setReturnCode(300);
            response.setReturnMessage("필수 useYn 파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        if(qstId == null){
            response.setReturnCode(300);
            response.setReturnMessage("필수 qstId 파라미터가 누락되었습니다.");
            return ResponseEntity.ok(response);
        }

        try{
            surveyService.updateQusetionUseYn(qstId, useYn);
        }catch (Exception e){
            response.setReturnCode(700);
            response.putContext("error",e.getMessage());
            response.setReturnMessage("시스템오류 입니다.");

        }

        return ResponseEntity.ok(response);
    }

    /**
     * 검색
     * @param search
     * @return
     */
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> surveySearch(@RequestParam @Valid SurveyBaseSearch search, Errors error){
        Response response = new Response();

        if(error.hasErrors()){
            response.setReturnCode(300);
            response.setReturnMessage("필수값이 누락되었습니다. ");
            return ResponseEntity.ok(response);
        }

        SurveyBase result =  surveyService.baseSearch(search.toSpec());

        return ResponseEntity.ok(response);
    }



}
