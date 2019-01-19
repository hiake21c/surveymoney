package com.surveymoney.controller.rest;

import com.surveymoney.bean.Response;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.repository.SurveyBaseRepository;
import com.surveymoney.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    @PutMapping(name = "putSurvey", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> putSurveyInfo(@ModelAttribute("survey") SurveyBase  survey){

        Response response = new Response();

        SurveyBase resultObj =  surveyService.insertSurveyInfo(survey);

        response.putContext("data",resultObj);

        return ResponseEntity.ok(response);
    }


}
