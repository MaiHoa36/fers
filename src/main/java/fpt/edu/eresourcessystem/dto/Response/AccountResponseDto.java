package fpt.edu.eresourcessystem.dto.Response;

import fpt.edu.eresourcessystem.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private String accountId;
    private String username;
    private String fullName;
    private String role;

    private String lastModifiedDate;

    public AccountResponseDto(Account account) {
        this.username = account.getUsername();
        this.fullName = account.getName();
        this.role = account.getRole().getDisplayValue();
        this.lastModifiedDate = account.getLastModifiedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
    }
}
