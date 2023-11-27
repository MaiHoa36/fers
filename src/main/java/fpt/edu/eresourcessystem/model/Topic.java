package fpt.edu.eresourcessystem.model;


import fpt.edu.eresourcessystem.dto.TopicDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document("topics")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"documents"})
public class Topic {
    @Id
    private String id;
    @NotNull
    @DocumentReference(lazy = true)
    private Course course;
    @DocumentReference(lazy = true)
    private List<fpt.edu.eresourcessystem.model.Document> documents;

    private String topicTitle;
    private String topicDescription;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    private LocalDate deletedDate;
    private Account deletedBy;

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
    public Topic(TopicDto topicDTO) {
        this.id = topicDTO.getId();
        this.course = topicDTO.getCourse();
        this.topicTitle = topicDTO.getTopicTitle();
        this.topicDescription = topicDTO.getTopicDescription();
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }


}
