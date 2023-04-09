package github.guilhermeabroncari.workdatepro.domain.response;

import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;

public record WorkDateUserResponse(Long id, String login) {
    public WorkDateUserResponse(WorkDateUser user) {
        this(user.getId(), user.getUsername());
    }
}
