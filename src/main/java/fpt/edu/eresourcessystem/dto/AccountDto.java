package fpt.edu.eresourcessystem.dto;

import fpt.edu.eresourcessystem.enums.AccountEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDto {

    private String id;

    private String password;

    @Email
    @NotEmpty(message = "account.validation.email.required")
    private String email;

    private String name;
    private LocalDate dateOfBirth;
    private AccountEnum.Gender gender;
    private AccountEnum.Campus campus;

    @NotNull(message = "account.validation.role.required")
    private AccountEnum.Role role;

    // Only use when response, no need in requests
    private AccountEnum.Status status;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
