package fpt.edu.eresourcessystem.model;

import fpt.edu.eresourcessystem.dto.DocumentDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.enums.CourseEnum;
import fpt.edu.eresourcessystem.enums.DocumentEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document("documents")
public class Document {
    @Id
    private String id;

    @NotEmpty(message = "course.validation.resourceType.required")
    @DocumentReference(lazy = true)
    private Topic topic;

    @DocumentReference(lazy = true)
    private Course course;

    private String courseId;

    @DocumentReference(lazy = true)
    private ResourceType resourceType;

    @NotEmpty(message = "course.validation.title.required")
    private String title;
    private String description;

    private String editorContent;

    private String suffix;
    private ObjectId contentId;
    private String cloudFileLink;
    private String fileName;
    private String fileDescription;
    @Lazy
    private String content;
    private boolean displayWithFile;

    @DocumentReference(lazy = true)
    private List<MultiFile> multipleFiles;

    private List<String> notes;
    private List<String> questions;

    private DocumentEnum.DocumentFormat docType;
    private CourseEnum.Status courseStatus;
    private DocumentEnum.DocumentStatusEnum docStatus;

    private List<String> students;

    @DocumentReference(lazy = true)
    private List<Rate> rates;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    private LocalDate deletedDate;
    private Account deletedBy;

    //Audit Log
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Document(DocumentDto documentDTO) {
        this.id = documentDTO.getId();
        this.topic = documentDTO.getTopic();
        this.resourceType = documentDTO.getResourceType();
        this.title = documentDTO.getTitle();
        this.description = documentDTO.getDescription();
        this.contentId = documentDTO.getContentId();
        this.content = documentDTO.getContent();
        this.editorContent = documentDTO.getEditorContent();
        this.cloudFileLink = documentDTO.getCloudFileLink();
        this.fileName = documentDTO.getFileName();
        this.displayWithFile = documentDTO.isDisplayWithFile();

        this.docStatus = documentDTO.getDocStatus();
        this.courseStatus = documentDTO.getTopic().getCourse().getStatus();

        this.suffix = documentDTO.getSuffix();
        this.docType = DocumentEnum.DocumentFormat.getDocType(documentDTO.getSuffix());
        this.multipleFiles = documentDTO.getMultiFiles();
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document document)) return false;
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public int getRate() {
        int sum = 0;
        for (Rate rate : this.rates) {
            sum += rate.getRate();
        }
        return sum / this.rates.size();
    }
}