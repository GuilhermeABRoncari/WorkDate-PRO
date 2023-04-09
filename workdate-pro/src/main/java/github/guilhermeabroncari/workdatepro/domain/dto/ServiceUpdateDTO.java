package github.guilhermeabroncari.workdatepro.domain.dto;

import java.math.BigDecimal;
import java.sql.Time;

public record ServiceUpdateDTO(
        String title,
        String description,
        BigDecimal value,
        Boolean hasPermanence,
        Time permanence
) {
}
