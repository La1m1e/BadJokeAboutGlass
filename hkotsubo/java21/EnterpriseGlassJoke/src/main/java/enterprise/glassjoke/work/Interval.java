package enterprise.glassjoke.work;

import enterprise.glassjoke.exception.IllegalIntervalBoundariesException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Defines an interval of hours.
 *
 * @author hkotsubo
 */
public class Interval {

    // max interval duration in seconds (can't be more than 1 hour)
    public static final int MAX_DURATION_SECS = 3600;

    protected LocalTime start, end;

    public Interval(LocalTime start, LocalTime end) {
        this.start = Objects.requireNonNull(start, "start time can't be null");
        this.end = Objects.requireNonNull(end, "end time can't be null");
        if (end.isBefore(start)) {
            throw new IllegalIntervalBoundariesException("end time can't be before start time");
        }
        if (ChronoUnit.SECONDS.between(start, end) > MAX_DURATION_SECS) {
            throw new IllegalIntervalBoundariesException("interval's duration can't be greater than 1 hour");
        }
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    /**
     * Check if the specified time is in this interval.
     *
     * @param time The time to check
     *
     * @return true if this interval contains the time, false otherwise
     */
    public boolean contains(LocalTime time) {
        return !time.isBefore(this.start) && time.isBefore(this.end);
    }
}
