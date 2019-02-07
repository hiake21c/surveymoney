package com.surveymoney.surveymoney.web;

import com.surveymoney.bean.Response;
import com.surveymoney.common.BaseTests;
import com.surveymoney.common.TestDscription;
import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.model.SurveyAnswerDto;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.JVM)
//@ActiveProfiles(inheritProfiles = false)
@Slf4j
public class SurveyTest extends BaseTests {



    @Test
    @TestDscription(description = "설문조사를 신규 등록한다.")
    public void insertSurvey() throws Exception {

        SurveyBaseDto search = new SurveyBaseDto();
        search.setTitle("Test");
        search.setState(SurveyState.OPEN);

        List<SurveyQuestionDto> questList = new ArrayList<SurveyQuestionDto>();

        IntStream.range(0,2).forEach(i->{
            SurveyQuestionDto questionDto = new SurveyQuestionDto();
            if(i == 0){
                questionDto.setQuestionTitle("test가 쉽습니까?");
            }else{
                questionDto.setQuestionTitle("test가 어렵습니까?");
            }
            questionDto.setQuestionType(QuestionType.SINGLE);

            List<SurveyAnswerDto> answerList = new ArrayList<>();
                IntStream.range(0,2).forEach(j->{
                    SurveyAnswerDto answerDto = new SurveyAnswerDto();

                    if(j == 0){
                        answerDto.setAnswerTile("YES");
                    }else{
                        answerDto.setAnswerTile("NO");
                    }
                    answerList.add(answerDto);
                });

            questionDto.setAnswers(answerList);
            questList.add(questionDto);
        });

        search.setQuestions(questList);
        String testDtoJson = mapToJson(search);

        MockHttpServletResponse mvcResult = mockMvc
                .perform(post("/survey/insertSurvey")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testDtoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnMessage").value("success"))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();

        String content = mvcResult.getContentAsString();
        Response resultDto = mapFromJson(content, Response.class);

        assertNotNull(resultDto.getContext());

    }

    @Test
    @TestDscription(description = "설문조사 상세조회를 한다.")
    public void detailSurvey()throws Exception{
        //mockMvc.perform(get("/findOne/{id}", 1L).accept(MediaType.APPLICATION_JSON))
        MockHttpServletResponse mvcResult = mockMvc
                .perform(get("/survey/detailSurvey")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .param("baseId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnMessage").value("success"))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();

        String content = mvcResult.getContentAsString();
        Response resultDto = mapFromJson(content, Response.class);

        assertThat(resultDto.getContext().get("data"), is(notNullValue()));
        assertThat(resultDto.getContext().get("resultId"), is(equalTo(1)));

    }



}
