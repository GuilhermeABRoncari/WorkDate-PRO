package github.guilhermeabroncari.workdatepro.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        @Email
        @NotBlank
        String login,
        @Size(min = 6)
        String password) {
}
