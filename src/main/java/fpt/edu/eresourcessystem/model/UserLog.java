package fpt.edu.eresourcessystem.model;

import fpt.edu.eresourcessystem.dto.UserLogDto;
import fpt.edu.eresourcessystem.enums.AccountEnum;
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
@Document("user_logs")
public class UserLog {
    @Id
    private String id;

    @NotNull
    private String url;
    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    private String email;
    @CreatedBy
    private String createdBy; // email
    @CreatedDate
    private LocalDateTime createdDate; // time

    private AccountEnum.Role role;

    public UserLog(UserLogDto userLogDto) {
        this.id = userLogDto.getId();
        this.url = userLogDto.getUrl();
        this.role = userLogDto.getRole();
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
        this.email = userLogDto.getEmail();
    }
}
