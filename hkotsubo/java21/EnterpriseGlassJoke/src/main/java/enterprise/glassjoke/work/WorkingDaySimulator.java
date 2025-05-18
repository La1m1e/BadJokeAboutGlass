package enterprise.glassjoke.work;

import enterprise.glassjoke.container.LiquidContainer;
import enterprise.glassjoke.container.LiquidType;
import enterprise.glassjoke.entity.Employee;
import enterprise.glassjoke.entity.Intern;
import enterprise.glassjoke.entity.factory.InternFactory;
import enterprise.glassjoke.thirsty.ThirstyFactorManager;
import enterprise.glassjoke.time.TimePassageManager;
import java.time.LocalTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simulates a working day.
 *
 * TODO: add support for multiple employees, each one with their own working schedule, multiple days, etc.
 *
 * @author hkotsubo
 */
public class WorkingDaySimulator {
    private static final Logger logger = LogManager.getLogger(WorkingDaySimulator.class.getSimpleName());

    private final WorkingSchedule workingSchedule;
    private final Employee employee;
    private final LiquidContainer liquidContainer;
    private final InternFactory internFactory;
    private final ThirstyFactorManager thirstyFactorManager;
    private final TimePassageManager<LocalTime> timePassageManager;

    /**
     * Create a working day simulator.
     *
     * @param workingSchedule The working schedule
     * @param employee The employee
     * @param liquidContainer The liquid container
     * @param internFactory The intern factory
     * @param thirstyFactorManager The thirsty factor manager
     * @param timePassageManager The time passage manager
     */
    public WorkingDaySimulator(WorkingSchedule workingSchedule, Employee employee, LiquidContainer liquidContainer,
            InternFactory internFactory, ThirstyFactorManager thirstyFactorManager, TimePassageManager<LocalTime> timePassageManager) {
        this.workingSchedule = workingSchedule;
        this.employee = employee;
        this.liquidContainer = liquidContainer;
        this.internFactory = internFactory;
        this.thirstyFactorManager = thirstyFactorManager;
        this.timePassageManager = timePassageManager;
    }

    /**
     * Simulates a working day.
     *
     * The simulations begins at the schedule's start hour, and each iteration advances time according to the time
     * passage manager's rules.
     *
     * In each iteration, it does the following:
     *
     * - if the employee is thirsty and the liquid container is empty, calls intern to fill it with some liquid
     *
     * TODO: currently it fills with water. Future versions might support different liquids depending on the situation
     * (e.g. it temperature is too cold, prefer tea or coffee, etc)
     *
     * - employee drinks: amount is defined by thirsty factor manager
     *
     * - if it's interval time (defined in the working schedule), the employee rests; otherwise the employee works
     *
     * - time passes, according to time passage manager's rules
     *
     * - thirsty factors might change in each iteration, according to thirsty factor manager's rules
     *
     * TODO: add more simulation types with algorithm variations (e.g. include boring meetings, which might affect
     * employee's stress and increase thirsty levels, there could be lazy or incompetent employees that create more work
     * for other employees, etc)
     */
    public void simulateWorkingDay() {
        // start working day
        LocalTime currentTime = workingSchedule.getStart();
        logger.info("It's {}, let's work until {}", currentTime, workingSchedule.getEnd());
        while (workingSchedule.inWorkingHours(currentTime)) {
            if (employee.isThirsty()) {
                if (liquidContainer.isEmpty()) {
                    Intern intern = employee.callIntern(internFactory);
                    intern.fill(liquidContainer, LiquidType.WATER);
                }
                employee.drink(liquidContainer, thirstyFactorManager);
            }
            employee.whatShouldBeDoing(workingSchedule, currentTime);
            currentTime = timePassageManager.nextMoment(currentTime);
            thirstyFactorManager.changeConditions();
        }
        logger.info("It's {}, go home!", currentTime);
    }

}
