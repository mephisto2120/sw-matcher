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
class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFoundWhenCustomerIdDoesNotExist() throws Exception {
        performGet("/person/get?customerId=-1")
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenFirstNameDoesNotExist() throws Exception {
        performGet("/person/get?firstName=Hikaru")
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenLastNameDoesNotExist() throws Exception {
        performGet("/person/get?lastName=Nakamura")
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPersonByCustomerId() throws Exception {
        ResultActions resultActions = performGet("/person/get?customerId=987");
        assertPersonData(resultActions);
    }

    @Test
    void shouldReturnPersonByFirstName() throws Exception {
        ResultActions resultActions = performGet("/person/get?firstName=John");
        assertPersonData(resultActions);
    }

    @Test
    void shouldReturnPersonByLastName() throws Exception {
        ResultActions resultActions = performGet("/person/get?lastName=Debtor");
        assertPersonData(resultActions);
    }

    @Test
    void shouldReturnPersonByCustomerIdAndLastName() throws Exception {
        ResultActions resultActions = performGet("/person/get?customerId=987&lastName=Debtor");
        assertPersonData(resultActions);
    }

    @Test
    void shouldReturnPersonByCustomerIdFirstAndLastName() throws Exception {
        ResultActions resultActions = performGet("/person/get?customerId=987&lastName=Debtor&firstName=John");
        assertPersonData(resultActions);
    }

    @Test
    void shouldReturnPersonByFirstAndLastName() throws Exception {
        ResultActions resultActions = performGet("/person/get?lastName=Debtor&firstName=John");
        assertPersonData(resultActions);
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url));
    }

    private static ResultActions assertPersonData(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].customerId", is(987)))
            .andExpect(jsonPath("$.[0].personId", is(999)))
            .andExpect(jsonPath("$.[0].firstName", is("John")))
            .andExpect(jsonPath("$.[0].lastName", is("Debtor")))
            .andExpect(jsonPath("$.[0].birthDate", is("1970-02-04")))
            .andExpect(jsonPath("$.[0].city", is("New York")))
            .andExpect(jsonPath("$.[0].street", is("Washington")))
            .andExpect(jsonPath("$.[0].houseNumber", is("24")))
            .andExpect(jsonPath("$.[0].email", is("john.debtor@gmail.com")));
    }
}
