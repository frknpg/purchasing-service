package com.emlakjet.purchasing.controller;

import com.emlakjet.purchasing.TestProfileConfiguration;
import com.emlakjet.purchasing.dao.invoice.InvoiceRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class represents a set of unit tests for the InvoiceController. It ensures that the controller
 * behaves correctly for different scenarios.
 *
 * @springBootTest This annotation is used to indicate that the test should run in the context of a Spring Boot application
 *                 with a random port.
 * @autoConfigureMockMvc This annotation is used to automatically configure the MockMvc instance for the test.
 * @activeProfiles This annotation specifies that the "test" profile should be active during the test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class InvoiceControllerTest implements TestProfileConfiguration {

    private static final String INVOICES_API_V1 = "/api/v1/invoices";

    private static final MediaType JSON_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * This method tests the behavior of adding an invoice with a sufficient limit and verifies that the response status is set to "accepted".
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void addInvoice_withSufficientLimit_getAcceptedStatus() throws Exception {
        var requestDTO = new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 100L, "TR0001", 123L);

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isOk());
    }

    /**
     * This method tests the behavior of adding an invoice with an amount over the limit,
     * and verifies that the response status is set to bad request.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void addInvoice_withOverLimit_getBadStatus() throws Exception {
        var requestDTO = new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 201L, "TR0001", 123L);

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isBadRequest());
    }

    /**
     * This method tests the behavior of adding two invoices with the same user and sufficient limit,
     * and checks if the status returned is accepted.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void addTwoInvoice_withSameUserSufficientLimit_getAcceptedStatus() throws Exception {
        var requestDTO = new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 100L, "TR0001", 123L);

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isOk());

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isOk());
    }

    /**
     * This method tests the behavior of adding two invoices with the same user and an amount over the limit. It verifies
     * that the response status is set to bad request.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void addTwoInvoice_withSameUserOverLimit_getBadStatus() throws Exception {
        var requestDTO = new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 101L, "TR0001", 123L);

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isOk());

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isBadRequest());
    }

    /**
     * This test method verifies the behavior of adding two invoices with different users and sufficient limit,
     * and checks if the status returned is accepted.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void addTwoInvoice_withDifferentUserSufficientLimit_getAcceptedStatus() throws Exception {
        var requestDTO = new InvoiceRequestDTO(
                "John", "Doe",
                "john@doe.com", 101L, "TR0001", 123L);
        var requestDTO2 = new InvoiceRequestDTO(
                "John", "Doe",
                "johndoe@mail.com", 101L, "TR0001", 123L);

        mvc.perform(createCreateInvoiceRequest(requestDTO))
                .andExpect(status().isOk());

        mvc.perform(createCreateInvoiceRequest(requestDTO2))
                .andExpect(status().isOk());
    }

    public MockHttpServletRequestBuilder createCreateInvoiceRequest(InvoiceRequestDTO requestDTO)
            throws JsonProcessingException {
        var body = objectMapper.writeValueAsString(requestDTO);

        return MockMvcRequestBuilders.post(INVOICES_API_V1)
                .content(body)
                .contentType(JSON_TYPE)
                .accept(JSON_TYPE);
    }

    /**
     * Clears the "invoice" table by deleting all rows using the JdbcTemplate.
     * This method is annotated with @AfterEach, which means it will be executed
     * after each test method in the InvoiceControllerTest class.
     */
    @AfterEach
    void clearTable() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "invoice");
    }

}
