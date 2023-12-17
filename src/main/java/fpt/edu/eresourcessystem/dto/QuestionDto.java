package fpt.edu.eresourcessystem.dto;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Answer;
import fpt.edu.eresourcessystem.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String id;

    private String content;

    private Student student;

    private fpt.edu.eresourcessystem.model.Document documentId;

    private Set<Answer> answers;

    private String lecturer;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    //Audit Log
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
