package enterprise.glassjoke.exception;

/**
 * Exception thrown when creating an interval with invalid times.
 *
 * @author hkotsubo
 */
public class IllegalIntervalBoundariesException extends EnterpriseGlassJokeBaseException {

    public IllegalIntervalBoundariesException() {
    }

    public IllegalIntervalBoundariesException(String message) {
        super(message);
    }

    public IllegalIntervalBoundariesException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalIntervalBoundariesException(Throwable cause) {
        super(cause);
    }
}
