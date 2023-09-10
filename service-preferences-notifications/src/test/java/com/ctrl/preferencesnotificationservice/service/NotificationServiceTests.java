package com.ctrl.preferencesnotificationservice.service;

import com.ctrl.preferencesnotificationservice.model.Notification;
import com.ctrl.preferencesnotificationservice.repository.NotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NotificationServiceTests {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void testaBuscaPorTodasAsNotificacoes() {
        List<Notification> notificationList = List.of(new Notification("1", "teste", true, "12345678"));

        when(notificationRepository.findAll()).thenReturn(notificationList);

        List<Notification> notificationListResult = notificationService.findAll();

        Assertions.assertThat(notificationListResult).isNotNull();
        Assertions.assertThat(notificationListResult.get(0)).isEqualTo(notificationList.get(0));

        verify(notificationRepository).findAll();
    }

    @Test
    void testaBuscaPorTodasAsNotificacoesQuandoNaoTemNotificacoes() {
        List<Notification> notificationList = new ArrayList<>();

        when(notificationRepository.findAll()).thenReturn(notificationList);

        List<Notification> notificationListResult = notificationService.findAll();

        Assertions.assertThat(notificationListResult).isEmpty();

        verify(notificationRepository).findAll();
    }

    @Test
    void testaBuscaNotificacaoPorId() {
        Notification notification = new Notification("1", "teste", true, "12345678");

        when(notificationRepository.findById("1")).thenReturn(Optional.of(notification));

        Notification notificationResult = notificationService.findById("1");

        Assertions.assertThat(notificationResult).isEqualTo(notification);

        verify(notificationRepository).findById("1");
    }

    @Test
    void testaInsercaoDeNotificaco() {
        Notification notification = new Notification("teste", true, "12345678");

        when(notificationService.insert(any(Notification.class))).thenReturn(notification);

        Notification notificationResult = notificationService.insert(notification);

        Assertions.assertThat(notificationResult).isEqualTo(notification);

        verify(notificationRepository).save(notification);
    }

}
