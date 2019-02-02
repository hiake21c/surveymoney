package com.surveymoney.tests.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.surveymoney.common.BaseTests;
import com.surveymoney.tests.model.TestDto;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class WebTest extends BaseTests {

    @Test
    public void get테스트() throws Exception {

        this.mockMvc.perform(get("/test/getTest")
                .param("id", "test")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(comparesEqualTo("success")));
    }

    @Test
    public void post테스트() throws Exception {
        TestDto paramDto = new TestDto();
        paramDto.setName("jjung4684");
        paramDto.setDescription("test입니다.");

        String testDtoJson = mapToJson(paramDto);

        this.mockMvc
                .perform(post("/test/postTest")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(testDtoJson))
                .andDo(print())
                .andExpect(model().attributeDoesNotExist("errors"))
                //.andExpect(model().hasNoErrors())

                .andReturn();
    }



}
