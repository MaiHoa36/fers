package fpt.edu.eresourcessystem.dto;


import fpt.edu.eresourcessystem.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("feedbacks")
public class FeedbackDto {
    @Id
    private String id;
    private Account account;
    private String feedbackEmotion;
    private String feedbackContent;
    private String status;

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}