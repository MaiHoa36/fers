package fpt.edu.eresourcessystem.dto;


import fpt.edu.eresourcessystem.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceTypeDto {
    @Id
    private String id;
    private String resourceTypeName;
    private Course course;

    // Only use when response, no need in requests
    private List<String> documents;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
