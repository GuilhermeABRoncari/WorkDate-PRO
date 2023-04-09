package github.guilhermeabroncari.workdatepro.infra.exception.exceptions;

public class LoginOrPasswordException extends RuntimeException {
    public LoginOrPasswordException(String message) {
        super(message);
    }
}
