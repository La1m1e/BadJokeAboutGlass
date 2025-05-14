package enterprise.glassjoke.work;

import enterprise.glassjoke.exception.IllegalIntervalBoundariesException;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Defines a generic working schedule, with start and end hours, and an optional interval.
 *
 * TODO: add support for more intervals?
 *
 * TODO: add support for extra hours (paid and unpaid, each one increase stress levels in differnt ways - which is not
 * implemented yet BTW)
 *
 * @author hkotsubo
 */
public class WorkingSchedule {

    protected LocalTime start, end;
    protected Interval interval;

    /**
     * Create a working schedule with start and end hours, and no interval.
     *
     * @param start When the schedule starts
     * @param end When the schedule ends
     */
    public WorkingSchedule(LocalTime start, LocalTime end) {
        this(start, end, null);
    }

    /**
     * Create a working schedule with start and end hours, and an interval.
     *
     * @param start When the schedule starts
     * @param end When the schedule ends
     * @param interval The interval, it must be between start and end hours
     */
    public WorkingSchedule(LocalTime start, LocalTime end, Interval interval) {
        this.start = Objects.requireNonNull(start, "start time can't be null");
        this.end = Objects.requireNonNull(end, "end time can't be null");
        if (end.isBefore(start)) {
            throw new IllegalIntervalBoundariesException("end time can't be before start time");
        }
        if (interval != null) {
            if (interval.getStart().isBefore(this.start) || interval.getEnd().isAfter(this.end)) {
                throw new IllegalIntervalBoundariesException("interval must be between working schedule hours");
            }
        }
        this.interval = interval;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Interval getInterval() {
        return interval;
    }

    /**
     * Check if we should be working at this time (AKA if time is between start and end hours).
     *
     * Basically, if start &lt;= time &lt; end then you should be working.
     *
     * @param time The time to check
     *
     * @return It returns true if it's working time, false otherwise
     */
    public boolean inWorkingHours(LocalTime time) {
        return !time.isBefore(this.start) && time.isBefore(this.end);
    }
}
