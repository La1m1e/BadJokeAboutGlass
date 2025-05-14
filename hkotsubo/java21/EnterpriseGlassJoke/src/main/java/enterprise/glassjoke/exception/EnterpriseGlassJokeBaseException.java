package enterprise.glassjoke.exception;

/**
 * Base exception for all application exceptions
 *
 * @author hkotsubo
 */
public class EnterpriseGlassJokeBaseException extends RuntimeException {

    public EnterpriseGlassJokeBaseException() {
    }

    public EnterpriseGlassJokeBaseException(String message) {
        super(message);
    }

    public EnterpriseGlassJokeBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterpriseGlassJokeBaseException(Throwable cause) {
        super(cause);
    }
}
