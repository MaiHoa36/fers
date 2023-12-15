package fpt.edu.eresourcessystem.controller.restcontrollers;

import fpt.edu.eresourcessystem.dto.Response.NotificationResponseDto;
import fpt.edu.eresourcessystem.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification")
public class NotificationRestController {
    private final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{accountId}")
    public List<NotificationResponseDto> getNotifications(@PathVariable(name = "accountId", required = false) String accountId) {
        List<NotificationResponseDto> notificationResponseDtos = notificationService.findByToAccount(accountId);
        return notificationResponseDtos;
    }
}
