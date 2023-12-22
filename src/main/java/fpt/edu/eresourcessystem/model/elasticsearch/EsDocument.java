package fpt.edu.eresourcessystem.model.elasticsearch;

import fpt.edu.eresourcessystem.enums.CourseEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Document(indexName = "documents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EsDocument implements Serializable {
    @Id
    private String documentId;
    @Field
    private String title;
    @Field
    private String description;
    @Field
    private String content;
    @Field
    private String docType;
    @Field
    private String lastModifiedDate;
    @Field
    private String createdBy;

    private String courseStatus;

    public EsDocument(fpt.edu.eresourcessystem.model.Document document) {
        this.documentId = document.getId();
        this.title = document.getTitle();
        this.description = document.getDescription();
        this.content = document.getContent();
        this.docType = document.getSuffix().toUpperCase();
        this.lastModifiedDate = document.getLastModifiedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
        this.createdBy = document.getCreatedBy();
        this.courseStatus = document.getCourseStatus().toString();
    }
}