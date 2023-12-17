package fpt.edu.eresourcessystem.dto;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseLogDto {
    private String id;

    private Course course;
    private String oldContent;
    private String newContent;
    private CommonEnum.Action action;
    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;
}
