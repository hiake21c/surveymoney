package com.surveymoney.surveymoney.web;

import com.surveymoney.bean.Response;
import com.surveymoney.common.BaseTests;
import com.surveymoney.common.TestDscription;
import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.enumulation.YesNoType;
import com.surveymoney.model.SurveyAnswerDto;
import com.surveymoney.model.SurveyBase;
import com.surveymoney.model.SurveyBaseDto;
import com.surveymoney.model.SurveyQuestionDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.JVM)
@Slf4j
public class SurveyTest extends BaseTests {

    @Test
    @TestDscription(description = "설문조사를 신규 등록한다.")
    public void surveyRegister() throws Exception {

        String testDtoJson = mapToJson(setSurveyBaseDto());

        MockHttpServletResponse mvcResult = mockMvc
                .perform(post("/survey/register")
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

        assertThat(resultDto.getContext(), is(notNullValue()));

    }

    private SurveyBaseDto setSurveyBaseDto() {
        SurveyBaseDto surveySearch = new SurveyBaseDto();
        surveySearch.setTitle("Test");
        surveySearch.setStateType(SurveyState.OPEN);
        surveySearch.setDisplayYn(YesNoType.Y);
        surveySearch.setUseYn(YesNoType.Y);
        List<SurveyQuestionDto> questList = setQuestionDto();
        surveySearch.setQuestions(questList);
        return surveySearch;
    }

    private List<SurveyQuestionDto> setQuestionDto() {
        List<SurveyQuestionDto> questList = new ArrayList<>();

        IntStream.range(0,2).forEach(i->{
            SurveyQuestionDto questionDto = new SurveyQuestionDto();
            questionDto.setDisplayYn(YesNoType.Y);
            questionDto.setUseYn(YesNoType.Y);
            questionDto.setQuestionType(QuestionType.SINGLE_CHOICE);

            if(i == 0){
                questionDto.setQuestionTitle("test가 쉽습니까?");
            }else{
                questionDto.setQuestionTitle("test가 어렵습니까?");
            }

            List<SurveyAnswerDto> answerList = setSurveyAnswer();

            questionDto.setAnswers(answerList);
            questList.add(questionDto);
        });

        return questList;
    }

    private List<SurveyAnswerDto> setSurveyAnswer() {
        List<SurveyAnswerDto> answerList = new ArrayList<>();
        IntStream.range(0,2).forEach(j->{
            SurveyAnswerDto answerDto = new SurveyAnswerDto();
            answerDto.setDisplayYn(YesNoType.Y);
            answerDto.setUseYn(YesNoType.Y);

            if(j == 0){
                answerDto.setAnswerContent("YES");
            }else{
                answerDto.setAnswerContent("NO");
            }
            answerList.add(answerDto);
        });
        return answerList;
    }

    @Test
    @TestDscription(description = "설문조사 목록을 조회 한다.")
    public void surveyAllList()throws Exception{
        MockHttpServletResponse mvcResult = mockMvc
                .perform(get("/survey/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("baseId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();

        String content = mvcResult.getContentAsString();
        JSONObject parseObj = new JSONObject(content);
        JSONArray returnObj = parseObj.getJSONObject("context").getJSONArray("data");
        assertThat(returnObj.length(), greaterThanOrEqualTo(0));
    }

    @Test
    @TestDscription(description = "설문조사 상세조회를 한다.")
    public void surveyDetail()throws Exception{
        //mockMvc.perform(get("/findOne/{id}", 1L).accept(MediaType.APPLICATION_JSON))
        MockHttpServletResponse mvcResult = mockMvc
                .perform(get("/survey/detail")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .param("baseId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnMessage").value("success"))
                .andExpect(jsonPath("$.returnCode").value("200"))
                //.andExpect(jsonPath("$.context.data.id").value("1"))
                .andReturn().getResponse();

//        String content = mvcResult.getContentAsString();
//        Response resultDto = mapFromJson(content, Response.class);
//        assertThat(resultDto.getContext().get("data"), is(notNullValue()));

        JSONObject returnObj = new JSONObject(mvcResult.getContentAsString()).getJSONObject("context").getJSONObject("data");
        assertThat(returnObj.get("id"), is(1));
    }

    @Test
    @TestDscription(description = "설문조사를 수정한다.")
    public void surveyUpdate() throws Exception{
        String testDtoJson = mapToJson(setSurveyBaseDto());

        MockHttpServletResponse mvcResult = mockMvc
                .perform(put("/survey/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testDtoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnMessage").value("success"))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();


    }

    @Test
    @TestDscription(description = "설문조사 질문을 삭제한다.")
    public void surveyQuestionDelete() throws Exception{
        MockHttpServletResponse mvcResult = mockMvc
                .perform(delete("/survey/questionDelete/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("qstId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnMessage").value("success"))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();
        String content = mvcResult.getContentAsString();
        Response resultDto = mapFromJson(content, Response.class);

        assertThat(resultDto.getContext().get("data"), is(notNullValue()));

    }

    @Test
    @TestDscription(description = "기본 설문조사를 삭제 한다.")
    public void surveyBaseDelete() throws Exception{
        MockHttpServletResponse mvcResult = mockMvc
                .perform(delete("/survey/baseDelete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("baseId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnCode").value("200"))
                .andReturn().getResponse();

        String content = mvcResult.getContentAsString();

    }

}
