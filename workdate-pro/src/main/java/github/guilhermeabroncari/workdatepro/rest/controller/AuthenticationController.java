package github.guilhermeabroncari.workdatepro.rest.controller;

import github.guilhermeabroncari.workdatepro.domain.dto.AuthenticationDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.WorkDateUserDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import github.guilhermeabroncari.workdatepro.domain.response.AuthenticationResponse;
import github.guilhermeabroncari.workdatepro.domain.response.WorkDateUserResponse;
import github.guilhermeabroncari.workdatepro.rest.service.WorkDateUserService;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.LoginOrPasswordException;
import github.guilhermeabroncari.workdatepro.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workdate")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private WorkDateUserService workDateUserService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if (!authenticate.isAuthenticated()) throw new LoginOrPasswordException("Email or password is invalid.");
            String tokenJWT = tokenService.generateToken((WorkDateUser) authenticate.getPrincipal());
            return new AuthenticationResponse(tokenJWT);
        } catch (AuthenticationException e) {
            throw new LoginOrPasswordException("Email or password is invalid.");
        }
    }

    @PostMapping("/sign")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkDateUserResponse sign(@RequestBody @Valid WorkDateUserDTO workDateUserDTO) {
        return workDateUserService.save(workDateUserDTO);
    }
}
