package com.surveymoney.tests.controller;

import com.surveymoney.tests.model.TestDto;
import com.surveymoney.tests.model.Tests;
import com.surveymoney.tests.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
//@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping(name = "/test/getTest")
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
    public ModelAndView postTest(ModelAndView mav, @RequestBody @Valid TestDto param, Errors errors) throws Exception {

        if (errors.hasErrors()) {
            mav.addObject("errors", errors.getFieldError());
            mav.addObject("code",300);
            mav.addObject("message","유효하지 않는 값이 존재 합니다.");
            return mav;
        }

        Tests testDo = new Tests();
        testDo.setName(param.getName());
        testDo.setDescription(param.getDescription());
        testRepository.save(testDo);

        log.debug("===>"+testDo.getId());
        if(testDo.getId() ==null) {

            mav.addObject("errors", errors.getFieldError());
            mav.addObject("code",999);
            mav.addObject("message","등록되지 않았습니다.");
            return mav;
        }

        mav.addObject("code",200);
        mav.addObject("message","success");

        return mav;
    }
}
