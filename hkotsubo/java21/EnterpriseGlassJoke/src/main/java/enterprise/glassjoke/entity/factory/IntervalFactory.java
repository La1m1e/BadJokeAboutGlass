package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.work.Interval;
import java.time.LocalTime;

/**
 * Factory to create intervals.
 *
 * @author hkotsubo
 */
public class IntervalFactory {

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    public static IntervalFactory newFactory() {
        return new IntervalFactory();
    }

    private IntervalFactory() {
    }

    /**
     * Create an interval with start and end hours.
     *
     * @param start When the interval starts
     * @param end When the interval ends
     *
     * @return The interval
     */
    public Interval createSchedule(LocalTime start, LocalTime end) {
        return new Interval(start, end);
    }

    /**
     * Create an interval with the start hour and the maximum duration (currently it's 1 hour)
     *
     * @param start When the interval starts
     *
     * @return An interval with the maximum duration (1 hours)
     */
    public Interval createIntervalWithMaxDuration(LocalTime start) {
        return new Interval(start, start.plusSeconds(Interval.MAX_DURATION_SECS));
    }
}
