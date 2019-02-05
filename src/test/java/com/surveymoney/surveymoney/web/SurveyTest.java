package com.surveymoney.surveymoney.web;

import com.surveymoney.bean.Response;
import com.surveymoney.common.BaseTests;
import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswerDto;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestionDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(inheritProfiles = false)
public class SurveyTest extends BaseTests {


    @Test
    public void survey1_설문조사_등록() throws Exception {

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
                .perform(post("/survey/insertSurvey")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testDtoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);

        Response resultDto = mapFromJson(content, Response.class);
        System.out.println(resultDto.getReturnCode());

        int resultCode = resultDto.getReturnCode();
        assertEquals(200, resultCode);
        assertNotNull(resultDto.getContext());

    }

    @Test
    public void survey2_설문조사_상세조회()throws Exception{
        //mockMvc.perform(get("/findOne/{id}", 1L).accept(MediaType.APPLICATION_JSON))
        MvcResult mvcResult = mockMvc
                .perform(get("/survey/detailSurvey")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .param("baseId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.baseId",is("1")))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);

        Response resultDto = mapFromJson(content, Response.class);


    }
}
