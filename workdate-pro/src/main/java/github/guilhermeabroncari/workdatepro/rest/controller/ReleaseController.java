package github.guilhermeabroncari.workdatepro.rest.controller;

import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;
import github.guilhermeabroncari.workdatepro.domain.response.ReleaseResponse;
import github.guilhermeabroncari.workdatepro.domain.response.Summary;
import github.guilhermeabroncari.workdatepro.rest.service.ReleaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("workdate/{user_id}/releases")
@AllArgsConstructor
public class ReleaseController {
    private ReleaseService releaseService;
    @GetMapping
    public List<ReleaseResponse> findAll(@PathVariable Long user_id) {
        return releaseService.findAll(user_id);
    }
    @GetMapping("{status}")
    public List<ReleaseResponse> findByStatus(@PathVariable Long user_id, @PathVariable ReleaseStatus status) {
        return releaseService.findByStatus(user_id, status);
    }
    @GetMapping("summary")
    public Summary summary(@PathVariable Long user_id) {
        return releaseService.summary(user_id);
    }
}
