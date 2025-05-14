package enterprise.glassjoke.time;

import java.time.temporal.Temporal;

/**
 * General interface for time passage managers.
 *
 * A time passage manager controls how time passes during a working day.
 *
 * @author hkotsubo
 *
 * @param <T> The type of the date/time object returned by this manager
 */
public interface TimePassageManager<T extends Temporal> {

    /**
     * Given a current date/time, returns the next date/time according to this manager's rules of how time passes.
     *
     * @param current The current date/time object
     *
     * @return The next date/time
     */
    public T nextMoment(T current);
}
