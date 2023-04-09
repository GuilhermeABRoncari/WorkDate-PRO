package github.guilhermeabroncari.workdatepro.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkDateUserDTO(
        @Email
        @NotBlank
        String loginEmail,
        @Size(min = 6)
        String password) {
}
