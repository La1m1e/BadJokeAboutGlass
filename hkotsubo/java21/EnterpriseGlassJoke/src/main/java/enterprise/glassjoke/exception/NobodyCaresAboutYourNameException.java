package enterprise.glassjoke.exception;

/**
 * Exception thrown when nobody cares about your name.
 * 
 * Currently used only for intern's names.
 * 
 * TODO: add more contexts, such as when a CEO is pretending to care, there's a small chance to not throw it, etc.
 *
 * @author hkotsubo
 */
public class NobodyCaresAboutYourNameException extends EnterpriseGlassJokeBaseException {

    public NobodyCaresAboutYourNameException() {
    }

    public NobodyCaresAboutYourNameException(String message) {
        super(message);
    }

    public NobodyCaresAboutYourNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NobodyCaresAboutYourNameException(Throwable cause) {
        super(cause);
    }
}
