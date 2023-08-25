package com.ctrl.integrationservice.controller;

import com.ctrl.integrationservice.IntegrationServiceApplication;
import com.ctrl.integrationservice.dto.IntegrationResponseDTO;
import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.service.IntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = IntegrationServiceApplication.class)
@AutoConfigureMockMvc
public class IntegrationControllerTest {

    @InjectMocks
    private IntegrationController integrationController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private IntegrationService integrationService;

    private UUID uuidTest = UUID.randomUUID();
    private String uuidTestUser = UUID.randomUUID().toString();
    private IntegrationResponseDTO integration;
    private Instant dateTimeTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mockMvc = MockMvcBuilders.standaloneSetup(integrationController).setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)).build();

        dateTimeTest = Instant.parse("2023-05-27T03:00:00Z");

        integration = new IntegrationResponseDTO();
        integration.setId(uuidTest);
        integration.setTitle("Teste");
        integration.setUrl("http://teste.com");
        integration.setPort("8080");
        integration.setInterval(30);
        integration.setDescription("Teste de Integração");
        integration.setCreatedAt(dateTimeTest);
        integration.setUpdatedAt(dateTimeTest);
        integration.setMonitoringAt(dateTimeTest);
        integration.setNextMonitoringAt(dateTimeTest);
        integration.setUserId(uuidTestUser);
    }

    @Test
    void retornaOkEListaDeIntegracoesQuandoBuscarIntegracoes() throws Exception {
        List<IntegrationResponseDTO> integrationList = List.of(integration);

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
    void retornaOkEListaDeIntegracoesDoUsuarioQuandoBuscarIntegracoesPeloIdDoUsuario() throws Exception {
        Mockito.when(integrationService.findByUserId(uuidTestUser)).thenReturn(List.of(integration));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/integrations/user/{userId}", uuidTestUser).contentType(MediaType.APPLICATION_JSON);

        String expected = "[{'id':'" + uuidTest + "'," +
                "'title':'Teste','url':'http://teste.com','port':'8080','interval':30,'description':'Teste de Integração'," +
                "'createdAt':'" + dateTimeTest + "','updatedAt':'" + dateTimeTest + "', 'monitoringAt':'" + dateTimeTest + "','nextMonitoringAt':'" + dateTimeTest + "'," +
                "'userId':'" + uuidTestUser + "'}]";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].userId").value(uuidTestUser))
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
