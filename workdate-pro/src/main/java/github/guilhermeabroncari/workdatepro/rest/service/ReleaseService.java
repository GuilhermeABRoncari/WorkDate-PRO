package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.entity.Release;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;
import github.guilhermeabroncari.workdatepro.domain.repository.ReleaseRepository;
import github.guilhermeabroncari.workdatepro.domain.repository.WorkDateUserRepository;
import github.guilhermeabroncari.workdatepro.domain.response.ReleaseResponse;
import github.guilhermeabroncari.workdatepro.domain.response.Summary;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReleaseService {
    private WorkDateUserRepository workDateUserRepository;
    private ReleaseRepository releaseRepository;

    public List<ReleaseResponse> findAll(Long userId) {
        isValid(userId);

        List<ReleaseResponse> list = new ArrayList<>();

        releaseRepository.findAll().forEach(
                release -> {
                    if (release.getUser().getId() == userId) list.add(new ReleaseResponse(release));
                });

        return list;
    }

    public List<ReleaseResponse> findByStatus(Long userId, ReleaseStatus status) {
        isValid(userId);
        var user = workDateUserRepository.getReferenceById(userId);

        List<Release> releaseList = releaseRepository.findAllByStatus(status);
        List<ReleaseResponse> list = new ArrayList<>();

        releaseList.forEach(release -> {
            if (user.getReleases().contains(release)) list.add(new ReleaseResponse(release));
        });

        return list;
    }

    public Summary summary(Long userId) {
        isValid(userId);
        var user = workDateUserRepository.getReferenceById(userId);

        BigDecimal totalOpen = releaseRepository.findAllByStatus(ReleaseStatus.OPEN)
                .stream()
                .filter(release -> user.getReleases().contains(release))
                .map(release -> release.getSchedule().getService().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPaid = releaseRepository.findAllByStatus(ReleaseStatus.PAID)
                .stream()
                .filter(release -> user.getReleases().contains(release))
                .map(release -> release.getSchedule().getService().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Summary(totalOpen, totalPaid, totalOpen.add(totalPaid));
    }

    private void isValid(Long userId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
    }
}
