package enterprise.glassjoke.exception;

/**
 * Exception thrown when the office entity's name is invalid
 *
 * @author hkotsubo
 */
public class InvalidOfficeEntityNameException extends EnterpriseGlassJokeBaseException {

    public InvalidOfficeEntityNameException() {
    }

    public InvalidOfficeEntityNameException(String message) {
        super(message);
    }

    public InvalidOfficeEntityNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOfficeEntityNameException(Throwable cause) {
        super(cause);
    }
}
