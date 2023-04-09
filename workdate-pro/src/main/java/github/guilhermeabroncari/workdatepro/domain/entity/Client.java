package github.guilhermeabroncari.workdatepro.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import github.guilhermeabroncari.workdatepro.domain.dto.ClientDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ClientUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Client")
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fone;
    private String address;
    private Boolean scheduled;
    @ManyToOne
    private WorkDateUser user;

    public void update(ClientUpdateDTO clientDTO) {
        if(clientDTO.name() != null) this.name = clientDTO.name();
        if(clientDTO.fone() != null) this.fone = clientDTO.fone();
        if(clientDTO.address() != null) this.address = clientDTO.address();
    }
}
