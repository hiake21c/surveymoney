package com.surveymoney.web;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surveymoney.controller.rest.TestController;
import com.surveymoney.model.TestDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application.properties")
public class RestWebTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .build();
    }


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
        paramDto.setName("jjung");
        paramDto.setDescription("TESTEST");

        String testDtoJson = objectMapper.writeValueAsString(paramDto);

        mockMvc
                .perform(post("/test/postTest")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(testDtoJson))
                .andExpect(model().hasNoErrors())
                .andExpect(model(). attribute("param",paramDto))
                .andExpect(view().name("testView"))
                .andReturn();
    }

}
