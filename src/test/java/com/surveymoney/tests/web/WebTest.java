package com.surveymoney.tests.web;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surveymoney.common.BaseTests;
import com.surveymoney.tests.model.TestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class WebTest extends BaseTests {

    @Test
    public void getTest() throws Exception {

        mockMvc.perform(get("/test/getTest")
                .param("id", "test")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(comparesEqualTo("success")));
    }

    @Test
    public void postTest() throws Exception {
        TestDto paramDto = new TestDto();
        paramDto.setName("jjung4684");
        paramDto.setDescription("TESTEST");

        String testDtoJson = objectMapper.writeValueAsString(paramDto);

        mockMvc
                .perform(post("/test/postTest")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(testDtoJson))
                .andDo(print())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(model().hasNoErrors())
                .andReturn();
    }

}
