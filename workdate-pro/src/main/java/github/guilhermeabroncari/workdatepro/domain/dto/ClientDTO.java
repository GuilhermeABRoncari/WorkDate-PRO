package github.guilhermeabroncari.workdatepro.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(
        @NotBlank
        String name,
        @NotBlank
        String fone,
        String address) {
}
