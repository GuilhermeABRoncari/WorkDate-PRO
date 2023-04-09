package github.guilhermeabroncari.workdatepro.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import github.guilhermeabroncari.workdatepro.domain.dto.ServiceUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Entity(name = "Service")
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Boolean hasPermanence = false;
    private Time permanence;
    private Boolean scheduled;
    @ManyToOne
    private WorkDateUser user;

    public void update(ServiceUpdateDTO serviceUpdateDTO) {
        if (serviceUpdateDTO.title() != null) this.title = serviceUpdateDTO.title();
        if (serviceUpdateDTO.description() != null) this.description = serviceUpdateDTO.description();
        if (serviceUpdateDTO.value() != null) this.price = serviceUpdateDTO.value();
        if (serviceUpdateDTO.hasPermanence() != null) this.hasPermanence = serviceUpdateDTO.hasPermanence();
        if (serviceUpdateDTO.permanence() != null) this.permanence = serviceUpdateDTO.permanence();
    }
}
