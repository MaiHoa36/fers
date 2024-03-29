package fpt.edu.eresourcessystem.dto;

import fpt.edu.eresourcessystem.enums.CourseEnum;
import fpt.edu.eresourcessystem.enums.DocumentEnum;
import fpt.edu.eresourcessystem.model.MultiFile;
import fpt.edu.eresourcessystem.model.ResourceType;
import fpt.edu.eresourcessystem.model.Student;
import fpt.edu.eresourcessystem.model.Topic;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDto {
    private String id;
    @NotNull
    private Topic topic;
    private String courseId;
    private ResourceType resourceType;

    @NotEmpty(message = "document.validation.title.required")
    private String title;
    private String description;

    private String editorContent;

    private String suffix;
    private ObjectId contentId;
    private String cloudFileLink;
    private String fileName;
    private String content;
    private String fileDescription;
    private boolean displayWithFile;
    private List<MultiFile> multiFiles;

    // Only use when response, no need in requests
    private DocumentEnum.DocumentStatusEnum docStatus;
    private List<Student> students;

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
