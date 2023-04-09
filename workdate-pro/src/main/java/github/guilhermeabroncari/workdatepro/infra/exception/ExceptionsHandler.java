package github.guilhermeabroncari.workdatepro.infra.exception;

import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity EmailInUse(EmailInUseException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(LoginOrPasswordException.class)
    public ResponseEntity LoginOrPasswordInvalid(LoginOrPasswordException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity userNotFound(UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity clientNotFound(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestJSONInfo(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity allReadyInUse(BusinessRuleException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
