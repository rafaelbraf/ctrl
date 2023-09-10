package com.ctrl.preferencesnotificationservice.controller;

import com.ctrl.preferencesnotificationservice.model.Notification;
import com.ctrl.preferencesnotificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTests {

    @InjectMocks
    private NotificationController notificationController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void retornaOkEListaDeNotificacoes() throws Exception {
        Notification notification = new Notification("1", "teste", true, "12345678");

        List<Notification> notificationList = List.of(notification);

        when(notificationService.findAll()).thenReturn(notificationList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences-notifications").contentType(MediaType.APPLICATION_JSON);

        String expected = "[{'id':'1','name':'teste','active':true,'userId':'12345678'}]";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaOkENotificacaoPorId() throws Exception {
        Notification notification = new Notification("1", "teste", true, "12345678");

        when(notificationService.findById(notification.getId())).thenReturn(notification);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences-notifications/{id}", notification.getId()).contentType(MediaType.APPLICATION_JSON);

        String expected = "{'id':'1','name':'teste','active':true,'userId':'12345678'}";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaOkEListaDeNotificacoesPeloIdDoUsuario() throws Exception {
        Notification notification = new Notification("1", "teste", true, "12345678");

        List<Notification> notificationList = List.of(notification);

        when(notificationService.findByUserId("12345678")).thenReturn(notificationList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences-notifications/user/{userId}", notification.getUserId()).contentType(MediaType.APPLICATION_JSON);

        String expected = "[{'id':'1','name':'teste','active':true,'userId':'12345678'}]";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaOkEListaDeNotificacoesVaziaQuandoUsuarioNaoTemNotificacoes() throws Exception {
        List<Notification> notificationList = new ArrayList<>();

        String userId = "123";

        when(notificationService.findByUserId(userId)).thenReturn(notificationList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences-notifications/user/{userId}", userId).contentType(MediaType.APPLICATION_JSON);

        String expected = "[]";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaCreatedENotificacaoInserida() throws Exception {
        Notification notification = new Notification("teste", true, "12345678");

        when(notificationService.insert(ArgumentMatchers.any(Notification.class))).thenReturn(notification);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/preferences-notifications/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(notification))
                .accept(MediaType.APPLICATION_JSON);

        String expected = "{'id':null,'name':'teste','active':true,'userId':'12345678'}";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void retornaOkQuandoAtualizarNotificacao() throws Exception {
        Notification notificationUpdated = new Notification("1", "teste", true, "12345678");

        when(notificationService.update(any(Notification.class))).thenReturn(notificationUpdated);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/preferences-notifications/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(notificationUpdated))
                .accept(MediaType.APPLICATION_JSON);

        String expected = "{'id':'1','name':'teste','active':true,'userId':'12345678'}";

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(notificationUpdated.getName()));
    }

    @Test
    void retornaNoContentQuandoExcluirNotificacao() throws Exception {
        doNothing().when(notificationService).delete(anyString());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/preferences-notifications/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(notificationService).delete("1");
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
