package enterprise.glassjoke.exception;

/**
 * Exception thrown when the amount to drink is invalid
 *
 * @author hkotsubo
 */
public class InvalidLiquidAmountException extends EnterpriseGlassJokeBaseException {

    public InvalidLiquidAmountException() {
    }

    public InvalidLiquidAmountException(String message) {
        super(message);
    }

    public InvalidLiquidAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLiquidAmountException(Throwable cause) {
        super(cause);
    }
}
