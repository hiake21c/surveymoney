package com.surveymoney.controller.rest;

import com.surveymoney.bean.Response;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
//@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping(name = "/test/getTest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTest(@RequestParam String id){

        String returnStr = "";

        if(id !=null && id !=""){
            returnStr = "success";
        }else{
            returnStr = "fail";
        }

        return returnStr;
    }
    @PostMapping(name = "/test/postTest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView postTest(ModelAndView mav, @RequestBody @Valid TestDto param, BindingResult bindingResult) throws BindException {

        log.debug("=======>1"+param);
        mav.addObject("param", param);
        mav.setViewName("testView");

        if (bindingResult.hasErrors()) {
            log.debug("=======>2");
            mav.addObject("errors", bindingResult.getFieldErrors());

            return mav;
        }

        //formService.saveData(param);

        return mav;
    }
}
