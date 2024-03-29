package fpt.edu.eresourcessystem.dto;


import fpt.edu.eresourcessystem.enums.DocumentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentNoteDto {
    private String id;
    private String studentId;
    private String title;
    private String description;
    private DocumentEnum.DocumentStatusEnum status;

    private DocumentEnum.DocumentFormat docType;
    private String editorContent;

    // Only use when response, no need in requests
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
