package com.surveymoney.surveymoney;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surveymoney.bean.Response;
import com.surveymoney.common.BaseTests;
import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswerDto;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestionDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SurveyTest extends BaseTests {


    @Test
    public void 설문조사등록() throws Exception {

        SurveyBaseDto search = new SurveyBaseDto();
        search.setTitle("Test");
        search.setState(SurveyState.OPEN);

        List<SurveyQuestionDto> questList = new ArrayList<SurveyQuestionDto>();

        for(int i=0; i<2; i++) {
            SurveyQuestionDto questionDto = new SurveyQuestionDto();
            if(i == 0) questionDto.setQuestionTitle("test가 쉽습니까?");
            else questionDto.setQuestionTitle("test가 어렵습니까?");
            questionDto.setQuestionType(QuestionType.SINGLE);


            List<SurveyAnswerDto> answerList = new ArrayList<SurveyAnswerDto>();
            for(int j=0; j<2; j++) {
                SurveyAnswerDto answerDto = new SurveyAnswerDto();
                if(j == 0) answerDto.setAnswerTile("YES");
                else answerDto.setAnswerTile("NO");
                answerList.add(answerDto);
            }

            questionDto.setAnswers(answerList);
            questList.add(questionDto);

        }
        search.setQuestions(questList);
        String testDtoJson = mapToJson(search);

        MvcResult mvcResult = mockMvc
                .perform(post("/survey/survey")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(testDtoJson))
                .andDo(print())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);

        Response resultDto = mapFromJson(content, Response.class);
        System.out.println(resultDto.getReturnCode());

        int resultCode = resultDto.getReturnCode();
        assertEquals(200, resultCode);
        assertTrue(resultDto != null);

    }

}
