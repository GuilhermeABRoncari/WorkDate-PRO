package github.guilhermeabroncari.workdatepro.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "Schedule")
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Client client;
    @OneToOne
    private Service service;
    private String observation;
    @Column(name = "schedule_time")
    private Timestamp scheduledTime;
    @Column(name = "status")
    private ScheduleStatus status;
    @ManyToOne
    private WorkDateUser user;
}
