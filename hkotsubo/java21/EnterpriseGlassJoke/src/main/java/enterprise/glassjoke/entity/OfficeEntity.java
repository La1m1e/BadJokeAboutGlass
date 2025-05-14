package enterprise.glassjoke.entity;

import enterprise.glassjoke.container.LiquidContainer;
import enterprise.glassjoke.container.LiquidType;
import enterprise.glassjoke.exception.InvalidOfficeEntityNameException;
import enterprise.glassjoke.thirsty.ThirstyFactorManager;
import enterprise.glassjoke.work.WorkingSchedule;
import java.time.LocalTime;
import java.util.Objects;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class that represents an office entity.
 *
 * An office entity is defined as "anyone that can be found in the office and has at least one function".
 *
 * Therefore, it can be either an employee, consultant, or even an intern.
 *
 * The current version supports only employees and interns (more types can be added in the future).
 *
 * @author hkotsubo
 */
public abstract class OfficeEntity {

    private static final String DEFAULT_NAME_WHEN_NONE_PROVIDED;

    static {
        String prop = System.getProperty("enterprise.glassjoke.entity.default_entity_name");
        if (isValidName(prop)) {
            DEFAULT_NAME_WHEN_NONE_PROVIDED = prop;
        } else {
            DEFAULT_NAME_WHEN_NONE_PROVIDED = "unknown";
        }
    }

    private static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        for (int i = 0; i < name.codePointCount(0, name.length()); i++) {
            int c = name.codePointAt(i);
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

    protected String name;
    protected OfficeEntityType type;
    protected boolean thirsty;
    protected Logger logger;

    /**
     * Create an office entity of a specific type.
     *
     * Use this when you don't care about the entity's name.
     *
     * If the system property "enterprise.glassjoke.entity.default_entity_name" is set, it'll be used as the default
     * name. Otherwise, the name will be set to "unknown".
     *
     * @param type The entity's type
     */
    public OfficeEntity(OfficeEntityType type) {
        this(DEFAULT_NAME_WHEN_NONE_PROVIDED, type);
    }

    /**
     * Create an office entity with a name and type.
     *
     * @param name The entity's name
     * @param type The entity's type
     */
    public OfficeEntity(String name, OfficeEntityType type) {
        if (!isValidName(name)) {
            throw new InvalidOfficeEntityNameException("Invalid name for office entity: " + name);
        }
        this.name = name;
        this.type = Objects.requireNonNull(type, "OfficeEntity's type must not be null");
    }

    /**
     * Get this entity's name
     *
     * @return The name of this entity
     */
    public String getName() {
        return name;
    }

    /**
     * Get this entity's type
     *
     * @return The type of this entity
     */
    public OfficeEntityType getType() {
        return type;
    }

    /**
     * Get this entity's thirsty status
     *
     * @return true if it's thirsty, false otherwise
     */
    public boolean isThirsty() {
        if (thirsty) {
            logger.info("I'm thirsty");
        }
        return thirsty;
    }

    /**
     * Change this entity's thirsty status
     *
     * @param thirsty The new thirsty status (true if it's thirsty, false otherwise)
     */
    public void setThirsty(boolean thirsty) {
        this.thirsty = thirsty;
    }

    public abstract void work();

    public abstract void enjoyInterval();

    /**
     * Fill a liquid container with a liquid type
     *
     * @param container The liquid container
     * @param liquidType The liquid type
     */
    public abstract void fill(LiquidContainer container, LiquidType liquidType);

    /**
     * Implementation of this entity drinking an amount of liquid from a container.
     *
     * The amount is defined by the thirsty factor manager
     *
     * @param container The liquid container
     * @param thirstyFactorManager The thirsty factor manager that calculates how thirsty this entity is
     */
    public void drink(LiquidContainer container, ThirstyFactorManager thirstyFactorManager) {
        // TODO: if the amount is greater than the container contents, maybe it should call the intern to refill and drink the remaining amount
        // Example: if there's 100ml but wants to drink 150ml, it should drink everything, calls intern to refill and drink more 50ml
        container.consume(thirstyFactorManager.getThirstyLevel(this), this);
    }

    /**
     * Check what this entity should be doing, according to their working schedule, at the specified time.
     *
     * If the time is not between the schedule working hours, it leaves the office (actually it just prints a message,
     * future versions might add an implementation for that).
     *
     * If the schedule has an interval and that interval contains the specified time, this entity enjoys the interval.
     * Otherwise it works.
     *
     * @param schedule The working schedule
     * @param time The time to chedk
     */
    public void whatShouldBeDoing(WorkingSchedule schedule, LocalTime time) {
        logger.info("It's {}, what should I do?", time);
        if (schedule.inWorkingHours(time)) {
            if (schedule.getInterval() != null && schedule.getInterval().contains(time)) {
                this.enjoyInterval();
            } else {
                this.work();
            }
            // whatever I do, I always get thirsty
            this.thirsty = true;
        } else {
            // TODO: add implementation for leaving office, staying home, going to the gym, doing anything else not related to work
            logger.info("Not my shift, bye!");
        }
    }
}
