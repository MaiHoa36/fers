package fpt.edu.eresourcessystem.dto.Response;

import fpt.edu.eresourcessystem.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private String id;
    private String notificationContent;
    private String type;
    private String status;
    private String lastModifiedDate;
    private String link;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.type = notification.getNotificationType().toString();
        this.status = notification.getNotificationStatus().toString();
        this.notificationContent = notification.getNotificationContent();
        this.link = notification.getLinkToView();
        this.lastModifiedDate = notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
    }
}
