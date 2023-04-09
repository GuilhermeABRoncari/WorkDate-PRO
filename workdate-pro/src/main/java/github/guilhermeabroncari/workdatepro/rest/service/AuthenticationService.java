package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.repository.WorkDateUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private WorkDateUserRepository workDateUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return workDateUserRepository.findByLogin(username);
    }
}
