package ma.enset.comptecqrses.commonapi.exceptions;

public class AmountNotSufficientException extends RuntimeException{
    public AmountNotSufficientException(String message) {
        super(message);
    }
}
