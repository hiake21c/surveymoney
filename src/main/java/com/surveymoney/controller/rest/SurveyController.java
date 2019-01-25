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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Response> putSurvey(@ModelAttribute @Valid SurveyBaseDto survey, BindingResult result){

        Response response = new Response();

        if(result.hasErrors()){

        }


        Long resultObj =  surveyService.insertSurveyInfo(survey);
        response.putContext("data",resultObj);

        return ResponseEntity.ok(response);
    }

    /**
     * Survey 모두 조회
     * @return
     */
//    @GetMapping(name = "surveyList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Response> getSurveyList(){
//
//        Response response = new Response();
//        List<SurveyBase> resultObj =  surveyService.getSurveyBaseAll();
//        response.putContext("data",resultObj);
//
//        return ResponseEntity.ok(response);
//    }

    /**
     * Survey 상세 조회
     * @param baseId
     * @return
     */
//    @GetMapping(name="surveyDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Response> getSurveyDetail(@RequestParam("baseId") Long baseId){
//
//        Response response = new Response();
//        SurveyBase resultObj =  surveyService.getSurveyBase(baseId);
//        response.putContext("data",resultObj);
//        return ResponseEntity.ok(response);
//    }


}
