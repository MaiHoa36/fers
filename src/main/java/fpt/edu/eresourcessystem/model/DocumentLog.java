package fpt.edu.eresourcessystem.model;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("document_logs")
public class DocumentLog {
    @Id
    private String id;

    @NotNull
    private String documentId;

    private CommonEnum.Action action;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    @CreatedBy
    private String account;
    @CreatedDate
    private LocalDateTime date;
}
