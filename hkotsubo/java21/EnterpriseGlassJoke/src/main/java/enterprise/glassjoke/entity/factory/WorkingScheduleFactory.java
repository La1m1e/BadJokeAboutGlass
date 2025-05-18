package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.time.TimeProvider;
import enterprise.glassjoke.work.Interval;
import enterprise.glassjoke.work.WorkingSchedule;
import java.time.LocalTime;

/**
 * Factory to create working schedules.
 *
 * @author hkotsubo
 */
public class WorkingScheduleFactory {

    private final TimeProvider timeProvider;

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    public static WorkingScheduleFactory newFactory() {
        return new WorkingScheduleFactory();
    }

    private WorkingScheduleFactory() {
        timeProvider = new TimeProvider();
    }

    /**
     * Create a nine-to-five schedule with an interval.
     *
     * If the schedule doesn't have an interval, just set it to null.
     *
     * @param interval The interval - use null for schedules without intervals
     *
     * @return The working schedule
     */
    public WorkingSchedule createNineToFiveSchedule(Interval interval) {
        return new WorkingSchedule(this.timeProvider.startOfNineToFive(), timeProvider.endOfNineToFive(), interval);
    }

    /**
     * Create a working schedule with start and end hours, and no interval.
     *
     * @param start When the schedule starts
     * @param end When the schedule ends
     *
     * @return The working schedule
     */
    public WorkingSchedule createSchedule(LocalTime start, LocalTime end) {
        return new WorkingSchedule(start, end);
    }

    /**
     * Create a working schedule with start and end hours, and an interval.
     *
     * @param start When the schedule starts
     * @param end When the schedule ends
     * @param interval The interval, it must be between start and end hours
     *
     * @return The working schedule
     */
    public WorkingSchedule createSchedule(LocalTime start, LocalTime end, Interval interval) {
        return new WorkingSchedule(start, end, interval);
    }
}
