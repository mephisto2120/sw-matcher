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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MatcherApplication.class)
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/customer/info?customerId=-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCompany() throws Exception {
        mockMvc.perform(get("/customer/info?customerId=123"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.customerId", is(123)))
            .andExpect(jsonPath("$.personId", nullValue()))
            .andExpect(jsonPath("$.companyId", is(111)))
            .andExpect(jsonPath("$.company", is(true)));
    }

    @Test
    void shouldReturnPerson() throws Exception {
        mockMvc.perform(get("/customer/info?customerId=987"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.customerId", is(987)))
            .andExpect(jsonPath("$.personId", is(999)))
            .andExpect(jsonPath("$.companyId", nullValue()))
            .andExpect(jsonPath("$.company", is(false)));
    }
}
