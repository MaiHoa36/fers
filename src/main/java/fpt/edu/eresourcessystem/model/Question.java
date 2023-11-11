package fpt.edu.eresourcessystem.model;


import fpt.edu.eresourcessystem.dto.CourseLogDTO;
import fpt.edu.eresourcessystem.dto.QuestionDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("questions")
public class Question {
    @Id
    private String id;

    @NotEmpty(message = "question.validation.question.required")
    private String content;

    @NotNull
    @DocumentReference(lazy = true)
    private Student student;

    @NotNull
    @DocumentReference(lazy = true)
    private fpt.edu.eresourcessystem.model.Document documentId;

    @DocumentReference(lazy = true)
    private List<Answer> answers;

    @DocumentReference(lazy = true)
    private Lecturer lecturer;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    //Audit Log
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private String createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private String lastModifiedDate;

    // Constructor DTO
    public Question(QuestionDto questionDto) {
        this.id = questionDto.getId();
        this.content = questionDto.getContent();
        this.student = questionDto.getStudent();
        this.documentId = questionDto.getDocumentId();
        this.answers = questionDto.getAnswers();
        this.lecturer = questionDto.getLecturer();
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }
}
