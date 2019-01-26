package com.surveymoney.tests.controller;

import com.surveymoney.tests.model.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

        if (bindingResult.hasErrors()) {
            mav.addObject("errors", bindingResult.getFieldErrors());
            mav.addObject("message","유효하지 않는 값이 존재 합니다.");
            return mav;
        }
        mav.addObject("code",200);
        mav.addObject("message","success");
        return mav;
    }
}
