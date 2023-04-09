package github.guilhermeabroncari.workdatepro.rest.controller;

import github.guilhermeabroncari.workdatepro.domain.dto.ScheduUpdateleDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ScheduleDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ScheduleStatusDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ScheduleStatus;
import github.guilhermeabroncari.workdatepro.domain.response.ScheduleResponse;
import github.guilhermeabroncari.workdatepro.rest.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workdate/{user_id}/schedules")
@AllArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse create(@PathVariable Long user_id, @RequestBody @Valid ScheduleDTO scheduleDTO) {
        return scheduleService.create(user_id, scheduleDTO);
    }

    @GetMapping
    public List<ScheduleResponse> findAll(@PathVariable Long user_id) {
        return scheduleService.findAll(user_id);
    }

    @GetMapping("{schedule_id}")
    public ScheduleResponse findById(@PathVariable Long user_id, @PathVariable Long schedule_id) {
        return scheduleService.findById(user_id, schedule_id);
    }

    @GetMapping("status-{status}")
    public List<ScheduleResponse> findByStatus(@PathVariable Long user_id, @PathVariable ScheduleStatus status) {
        return scheduleService.findByStatus(user_id, status);
    }

    @PutMapping("{schedule_id}")
    public ScheduleResponse update(
            @PathVariable Long user_id, @PathVariable Long schedule_id,
            @RequestBody ScheduUpdateleDTO scheduUpdateleDTO) {
        return scheduleService.update(user_id, schedule_id, scheduUpdateleDTO);
    }

    @PatchMapping("{schedule_id}")
    public ScheduleResponse patch(@PathVariable Long user_id, @PathVariable Long schedule_id, @RequestBody ScheduleStatusDTO scheduleStatusDTO) {
        return scheduleService.patch(user_id, schedule_id, scheduleStatusDTO);
    }

    @DeleteMapping("{schedule_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long user_id, @PathVariable Long schedule_id) {
        scheduleService.delete(user_id, schedule_id);
    }
}
