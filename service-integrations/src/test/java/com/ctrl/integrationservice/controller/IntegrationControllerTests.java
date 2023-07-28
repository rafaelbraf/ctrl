package com.ctrl.integrationservice.controller;

import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.service.IntegrationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@WebMvcTest(IntegrationController.class)
public class IntegrationControllerTests {

    @Autowired
    private IntegrationController integrationController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntegrationService integrationService;

    private UUID uuidTest = UUID.randomUUID();
    private String uuidTestUser = UUID.randomUUID().toString();
    private Integration integration;
    private Instant dateTimeTest;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(integrationController);

        dateTimeTest = Instant.parse("2023-05-27T03:00:00Z");

        integration = new Integration(
                "Teste",
                "http://teste.com",
                "8080",
                30,
                "Teste de Integração",
                dateTimeTest,
                dateTimeTest,
                dateTimeTest,
                dateTimeTest,
                uuidTestUser
        );

        integration.setId(uuidTest);
    }

    @Test
    void retornaOkEListaDeIntegracoesQuandoBuscarIntegracoes() throws Exception {
        List<Integration> integrationList = List.of(integration);

        Mockito.when(integrationService.findAll()).thenReturn(integrationList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/integrations").contentType(MediaType.APPLICATION_JSON);

        String expected = "[{'id':'" + uuidTest + "'," +
                "'title':'Teste','url':'http://teste.com','port':'8080','interval':30,'description':'Teste de Integração'," +
                "'createdAt':'" + dateTimeTest + "','updatedAt':'" + dateTimeTest + "', 'monitoringAt':'" + dateTimeTest + "','nextMonitoringAt':'" + dateTimeTest + "'," +
                "'userId':'" + uuidTestUser + "'}]";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaOkEIntegracaoQuandoBuscarIntegracaoPorId() throws Exception {
        Mockito.when(integrationService.findById(uuidTest)).thenReturn(integration);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/integrations/{id}", integration.getId()).contentType(MediaType.APPLICATION_JSON);

        String expected = "{'id':'" + uuidTest + "'," +
                "'title':'Teste','url':'http://teste.com','port':'8080','interval':30,'description':'Teste de Integração'," +
                "'createdAt':'" + dateTimeTest + "','updatedAt':'" + dateTimeTest + "', 'monitoringAt':'" + dateTimeTest + "','nextMonitoringAt':'" + dateTimeTest + "'," +
                "'userId':'" + uuidTestUser + "'}";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(uuidTest.toString()))
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaCreatedEIntegracaoSalvaQuandoSalvarIntegracao() throws Exception {
        Mockito.when(integrationService.create(ArgumentMatchers.any(Integration.class))).thenReturn(integration);

        String contentRequest = "{\"title\":\"Teste\",\"url\":\"http://teste.com\",\"port\":\"8080\",\"interval\":30,\"description\":\"Teste de Integração\",\"userId\":\"" + uuidTestUser + "\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/integrations/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentRequest)
                .accept(MediaType.APPLICATION_JSON);

        String expected = "{'id':'" + uuidTest + "'," +
                "'title':'Teste','url':'http://teste.com','port':'8080','interval':30,'description':'Teste de Integração'," +
                "'createdAt':'" + dateTimeTest + "','updatedAt':'" + dateTimeTest + "', 'monitoringAt':'" + dateTimeTest + "','nextMonitoringAt':'" + dateTimeTest + "'," +
                "'userId':'" + uuidTestUser + "'}";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

}
