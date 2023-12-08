package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.TestProfileConfiguration;
import com.emlakjet.purchasing.dao.authentication.AuthenticationRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest implements TestProfileConfiguration {

    private static final String LOGIN_API_V1 = "/api/v1/auth/login";

    private static final MediaType JSON_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login_withCorrectCredentials_getOkStatus() throws Exception {
        var requestDTO = new AuthenticationRequestDTO("john@doe.com", "demopass");

        mvc.perform(createCreateLoginRequest(requestDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void login_withBadCredentials_getBadStatus() throws Exception {
        var requestDTO = new AuthenticationRequestDTO("john@doe.com", "demopass2");

        mvc.perform(createCreateLoginRequest(requestDTO))
                .andExpect(status().is4xxClientError());
    }

    public MockHttpServletRequestBuilder createCreateLoginRequest(AuthenticationRequestDTO requestDTO)
            throws JsonProcessingException {
        var body = objectMapper.writeValueAsString(requestDTO);

        return MockMvcRequestBuilders.post(LOGIN_API_V1)
                .content(body)
                .contentType(JSON_TYPE)
                .accept(JSON_TYPE);
    }
}
