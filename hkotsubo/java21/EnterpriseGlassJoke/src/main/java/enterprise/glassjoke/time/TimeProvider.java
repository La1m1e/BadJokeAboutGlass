package enterprise.glassjoke.time;

import java.time.LocalTime;

/**
 * Provider for date/time values
 *
 * @author hkotsubo
 */
public class TimeProvider {

    public LocalTime now() {
        return LocalTime.now();
    }

    public LocalTime middleOfDay() {
        return LocalTime.NOON;
    }

    public LocalTime startOfNineToFive() {
        return LocalTime.of(9, 0);
    }

    public LocalTime endOfNineToFive() {
        return LocalTime.of(17, 0);
    }
}
