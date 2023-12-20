package fpt.edu.eresourcessystem.model;


import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("rates")
public class Rate {
    @Id
    private String id;
    @NotNull
    private String studentId;
    @NotNull
    private String documentId;

    // 1 - 5
    private Integer rate;

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

    public Rate(String id, @NotNull String studentId, @NotNull String documentId, Integer rate) {
        this.id = id;
        this.studentId = studentId;
        this.documentId = documentId;
        this.rate = rate;
    }
}
