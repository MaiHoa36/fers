package fpt.edu.eresourcessystem.model;


import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotNull;
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
@Document("lecturers")
public class Lecturer {
    @Id
    private String id;

    @NotNull
    @DocumentReference(lazy = true)
    private Account account;


    private List<String> lecturerCourses;

    @DocumentReference(lazy = true)
    private List<Course> courses;

    @DocumentReference(lazy = true)
    private List<String> documents;
    @DocumentReference(lazy = true)
    private List<String> answers;

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

    // Constructor
    public Lecturer(@NotNull Account account) {
        this.account = account;
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }


}
