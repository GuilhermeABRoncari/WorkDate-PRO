package github.guilhermeabroncari.workdatepro.infra.exception.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
