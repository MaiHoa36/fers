package fpt.edu.eresourcessystem.model;


import fpt.edu.eresourcessystem.dto.TrainingTypeDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("training_types")
public class TrainingType {
    @Id
    private String id;
    @NotEmpty
    private String trainingTypeName;
    private String trainingTypeDescription;

    @DocumentReference(lazy = true)
    private List<Course> courses;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    //Audit Log
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // Constructor DTO
    public TrainingType(TrainingTypeDto trainingTypeDTO) {
        this.id = trainingTypeDTO.getId();
        this.trainingTypeName = trainingTypeDTO.getTrainingTypeName();
        this.trainingTypeDescription = trainingTypeDTO.getTrainingTypeDescription();
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }
}
