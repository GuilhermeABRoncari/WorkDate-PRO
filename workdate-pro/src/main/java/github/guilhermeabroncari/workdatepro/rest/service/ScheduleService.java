package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.dto.ScheduUpdateleDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ScheduleDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ScheduleStatusDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.Release;
import github.guilhermeabroncari.workdatepro.domain.entity.Schedule;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ScheduleStatus;
import github.guilhermeabroncari.workdatepro.domain.repository.*;
import github.guilhermeabroncari.workdatepro.domain.response.ScheduleResponse;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.BusinessRuleException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.EntityNotFoundException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private ClientRepository clientRepository;
    private WorkDateUserRepository workDateUserRepository;
    private WorkJobRepository workJobRepository;
    private ScheduleRepository scheduleRepository;
    private ServiceRepository serviceRepository;
    private ReleaseRepository releaseRepository;
    private static final String ALL_READY_IN = "This schedule is already ";

    @Transactional
    public ScheduleResponse create(Long userId, ScheduleDTO scheduleDTO) {
        isValid(userId, scheduleDTO.client_id(), scheduleDTO.service_id());

        var user = workDateUserRepository.getReferenceById(userId);
        var client = clientRepository.getReferenceById(scheduleDTO.client_id());
        var service = workJobRepository.getReferenceById(scheduleDTO.service_id());

        if (!user.getClients().contains(client)) throw new EntityNotFoundException();
        if (!user.getServices().contains(service)) throw new EntityNotFoundException();

        client.setScheduled(true);
        service.setScheduled(true);
        clientRepository.save(client);
        serviceRepository.save(service);

        isTimeValid(scheduleDTO.scheduleTime());

        var schedule = new Schedule(null, client, service, scheduleDTO.observation(), scheduleDTO.scheduleTime(), ScheduleStatus.OPEN, user);
        scheduleRepository.save(schedule);
        user.getSchedules().add(schedule);


        var release = new Release(null, schedule, ReleaseStatus.OPEN, user);
        releaseRepository.save(release);

        user.getReleases().add(release);
        workDateUserRepository.save(user);

        return new ScheduleResponse(schedule);
    }

    public List<ScheduleResponse> findAll(Long userId) {
        isValid(userId);
        List<ScheduleResponse> list = new ArrayList<>();
        scheduleRepository.findAll().forEach(
                schedule -> {
                    if (schedule.getUser().getId() == userId) list.add(new ScheduleResponse(schedule));
                });
        return list;
    }

    @Transactional
    public ScheduleResponse findById(Long userId, Long scheduleId) {
        isValid(userId, scheduleId);
        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getSchedules().contains(scheduleRepository.getReferenceById(scheduleId))) {
            return new ScheduleResponse(scheduleRepository.getReferenceById(scheduleId));
        }
        throw new EntityNotFoundException();
    }

    @Transactional
    public List<ScheduleResponse> findByStatus(Long userId, ScheduleStatus status) {
        isValid(userId);
        var user = workDateUserRepository.getReferenceById(userId);

        List<Schedule> scheduleList = scheduleRepository.findAllByStatus(status);
        List<ScheduleResponse> list = new ArrayList<>();

        scheduleList.forEach(schedule -> {
            if (user.getSchedules().contains(schedule)) list.add(new ScheduleResponse(schedule));
        });

        return list;
    }

    @Transactional
    public ScheduleResponse update(Long userId, Long scheduleId, ScheduUpdateleDTO scheduUpdateleDTO) {
        isValid(userId, scheduleId);
        var user = workDateUserRepository.getReferenceById(userId);
        var schedule = scheduleRepository.getReferenceById(scheduleId);

        if (!user.getSchedules().contains(schedule)) throw new EntityNotFoundException();

        if (scheduUpdateleDTO.observation() != null) schedule.setObservation(scheduUpdateleDTO.observation());

        if (scheduUpdateleDTO.client_id() != null) {
            var removedClient = schedule.getClient();
            removedClient.setScheduled(false);
            clientRepository.save(removedClient);
            schedule.setClient(null);

            var client = clientRepository.getReferenceById(scheduUpdateleDTO.client_id());

            client.setScheduled(true);
            clientRepository.save(client);
            schedule.setClient(client);
        }
        if (scheduUpdateleDTO.service_id() != null) {
            var removedService = schedule.getService();
            removedService.setScheduled(false);
            serviceRepository.save(removedService);
            schedule.setService(null);

            var service = serviceRepository.getReferenceById(scheduUpdateleDTO.service_id());

            service.setScheduled(true);
            serviceRepository.save(service);
            schedule.setService(service);
        }
        scheduleRepository.save(schedule);
        return new ScheduleResponse(schedule);
    }

    @Transactional
    public ScheduleResponse patch(Long userId, Long scheduleId, ScheduleStatusDTO scheduleStatusDTO) {
        isValid(userId, scheduleId);
        var user = workDateUserRepository.getReferenceById(userId);
        var schedule = scheduleRepository.getReferenceById(scheduleId);

        if (!user.getSchedules().contains(schedule)) throw new EntityNotFoundException();

        if (schedule.getStatus() == scheduleStatusDTO.schedule_status())
            throw new BusinessRuleException(ALL_READY_IN + schedule.getStatus());

        schedule.setStatus(scheduleStatusDTO.schedule_status());
        scheduleRepository.save(schedule);

        return new ScheduleResponse(schedule);
    }

    @Transactional
    public void delete(Long userId, Long scheduleId) {
        isValid(userId, scheduleId);
        var user = workDateUserRepository.getReferenceById(userId);
        var schedule = scheduleRepository.getReferenceById(scheduleId);

        if (!user.getSchedules().contains(schedule)) throw new EntityNotFoundException();
        user.getSchedules().remove(schedule);
        workDateUserRepository.save(user);

        var client = schedule.getClient();
        var service = schedule.getService();

        client.setScheduled(false);
        service.setScheduled(false);
        clientRepository.save(client);
        serviceRepository.save(service);

        scheduleRepository.delete(schedule);
    }

    private void isValid(Long userId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
    }

    private void isValid(Long userId, Long scheduleId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
        if (!scheduleRepository.existsById(scheduleId)) throw new EntityNotFoundException();
    }

    private void isValid(Long userId, Long clientId, Long serviceId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
        if (!clientRepository.existsById(clientId)) throw new EntityNotFoundException();
        if (!workJobRepository.existsById(serviceId)) throw new EntityNotFoundException();
    }

    private void isTimeValid(Timestamp scheduledTime) {
        if (scheduleRepository.existsByScheduledTime(scheduledTime)) throw new BusinessRuleException("");
    }
}
