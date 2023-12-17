package fpt.edu.eresourcessystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNoteDto {
    private String id;
    private String studentId;

    private String content;
    private String docId;
    private String documentTitle;

    // Only use when response, no need in requests
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
