package github.guilhermeabroncari.workdatepro.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Time;

public record ServiceDTO(
        @NotBlank
        String title,
        String description,
        @NotNull
        BigDecimal value,
        @NotNull
        Boolean hasPermanence,
        Time permanence
) {
}
