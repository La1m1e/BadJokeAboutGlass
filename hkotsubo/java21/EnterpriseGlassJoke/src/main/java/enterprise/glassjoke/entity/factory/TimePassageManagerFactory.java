package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.time.TimePassageManager;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * Factory to create time passage managers.
 *
 * @author hkotsubo
 */
public class TimePassageManagerFactory {

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    public static TimePassageManagerFactory newFactory() {
        return new TimePassageManagerFactory();
    }

    private TimePassageManagerFactory() {
    }

    /**
     * Create a time passage manager that adds one hour to the current date/time
     *
     * @param <T> The type of the date/time object returned by the manager
     * @return The time passage manager
     */
    public <T extends Temporal> TimePassageManager<T> createOneHourTimePassageManager() {
        return createTimePassageManager(1, ChronoUnit.HOURS);
    }

    /**
     * Create a time passage manager that adds a specific amount of time to the current date/time
     *
     * @param <T> The type of the date/time object returned by the manager
     *
     * @param amount The amount to add
     * @param unit The unit of time to add
     *
     * @return The time passage manager
     */
    public <T extends Temporal> TimePassageManager<T> createTimePassageManager(long amount, TemporalUnit unit) {
        return t -> (T) t.plus(amount, unit);
    }
}
