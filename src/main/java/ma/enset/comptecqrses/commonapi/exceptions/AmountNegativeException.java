package ma.enset.comptecqrses.commonapi.exceptions;

public class AmountNegativeException extends RuntimeException{
    public AmountNegativeException(String message) {
        super(message);
    }
}
