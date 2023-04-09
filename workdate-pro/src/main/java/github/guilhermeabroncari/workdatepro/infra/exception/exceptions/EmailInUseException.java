package github.guilhermeabroncari.workdatepro.infra.exception.exceptions;

public class EmailInUseException extends RuntimeException {
    public EmailInUseException(String emailInUse) {
        super(emailInUse);
    }
}
