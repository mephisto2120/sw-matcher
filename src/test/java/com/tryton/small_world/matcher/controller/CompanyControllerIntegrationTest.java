package com.tryton.small_world.matcher.controller;

import com.tryton.small_world.matcher.MatcherApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MatcherApplication.class)
@AutoConfigureMockMvc
class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFound() throws Exception {
        performGet("/company/get?customerId=-1")
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCompanyByCustomerId() throws Exception {
        ResultActions resultActions = performGet("/company/get?customerId=123");
        assertCompanyData(resultActions);
    }

    @Test
    void shouldReturnCompanyByName() throws Exception {
        ResultActions resultActions = performGet("/company/get?name=Scylla");
        assertCompanyData(resultActions);
    }

    @Test
    void shouldReturnCompanyByCustomerIdAndName() throws Exception {
        ResultActions resultActions = performGet("/company/get?customerId=123&name=Scylla");
        assertCompanyData(resultActions);
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url));
    }

    private void assertCompanyData(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].customerId", is(123)))
            .andExpect(jsonPath("$.[0].companyId", is(111)))
            .andExpect(jsonPath("$.[0].name", is("Scylla")))
            .andExpect(jsonPath("$.[0].city", is("New York")))
            .andExpect(jsonPath("$.[0].street", is("Washington")))
            .andExpect(jsonPath("$.[0].localNumber", is("24")))
            .andExpect(jsonPath("$.[0].email", is("scylla@gmail.com")));
    }
}
