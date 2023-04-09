package github.guilhermeabroncari.workdatepro.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Release")
@Table(name = "releases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Schedule schedule;
    @Column(name = "status")
    private ReleaseStatus status;
    @ManyToOne
    private WorkDateUser user;
}
