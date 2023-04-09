package github.guilhermeabroncari.workdatepro.rest.controller;

import github.guilhermeabroncari.workdatepro.domain.dto.ServiceDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ServiceUpdateDTO;
import github.guilhermeabroncari.workdatepro.domain.response.ServiceResponse;
import github.guilhermeabroncari.workdatepro.rest.service.ServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workdate/{user_id}/services")
@AllArgsConstructor
public class ServiceController {
    private ServiceService serviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse saveService(@PathVariable Long user_id, @RequestBody @Valid ServiceDTO serviceDTO) {
        return serviceService.save(user_id, serviceDTO);
    }

    @GetMapping
    public List<ServiceResponse> findAllWorkJobs(@PathVariable Long user_id) {
        return serviceService.list(user_id);
    }

    @GetMapping("/{service_id}")
    public ServiceResponse findWorkJobById(@PathVariable Long user_id, @PathVariable Long service_id) {
        return serviceService.find(user_id, service_id);
    }

    @PutMapping("/{service_id}")
    public ServiceResponse updateService(@PathVariable Long user_id, @PathVariable Long service_id, @RequestBody ServiceUpdateDTO serviceUpdateDTO) {
        return serviceService.update(user_id, service_id, serviceUpdateDTO);
    }

    @DeleteMapping("/{service_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long user_id, @PathVariable Long service_id) {
        serviceService.delete(user_id, service_id);
    }
}
