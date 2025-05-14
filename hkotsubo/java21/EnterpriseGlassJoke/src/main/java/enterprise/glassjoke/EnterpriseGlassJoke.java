package enterprise.glassjoke;

import enterprise.glassjoke.container.LiquidContainer;
import enterprise.glassjoke.container.LiquidContainerBuilder;
import enterprise.glassjoke.container.LiquidContainerType;
import enterprise.glassjoke.container.LiquidType;
import enterprise.glassjoke.entity.Employee;
import enterprise.glassjoke.entity.Intern;
import enterprise.glassjoke.entity.factory.EmployeeFactory;
import enterprise.glassjoke.entity.factory.InternFactory;
import enterprise.glassjoke.entity.factory.IntervalFactory;
import enterprise.glassjoke.entity.factory.TimePassageManagerFactory;
import enterprise.glassjoke.entity.factory.WorkingScheduleFactory;
import enterprise.glassjoke.thirsty.ThirstyFactorBuilder;
import enterprise.glassjoke.thirsty.ThirstyFactorManager;
import enterprise.glassjoke.time.TimePassageManager;
import enterprise.glassjoke.time.TimeProvider;
import enterprise.glassjoke.work.Interval;
import enterprise.glassjoke.work.WorkingDaySimulator;
import enterprise.glassjoke.work.WorkingSchedule;
import java.time.LocalTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Overly exagerated "enterprise" implementation of the glass code joke.
 *
 * @author hkotsubo
 */
public class EnterpriseGlassJoke {

    private static final Logger logger = LogManager.getLogger(EnterpriseGlassJoke.class.getSimpleName());

    public static void main(String[] args) {
        // build factories needed for the simulation
        EmployeeFactory employeeFactory = new EmployeeFactory();
        InternFactory internFactory = new InternFactory();
        TimeProvider timeProvider = new TimeProvider();
        TimePassageManagerFactory timePassageManagerFactory = TimePassageManagerFactory.newFactory();

        LiquidContainerBuilder liquidContainerBuilder = new LiquidContainerBuilder();
        LiquidContainer glass = liquidContainerBuilder
                .withCapacity(750) // A glass with 750ml capacity
                .withType(LiquidContainerType.GLASS)
                .addContent(LiquidType.WATER, 500) // starts with some water
                .build();

        // some employee
        Employee employee = employeeFactory.createEntity("John Doe");

        // 9-to-5 working schedule
        WorkingScheduleFactory scheduleFactory = WorkingScheduleFactory.newFactory();
        IntervalFactory intervalFactory = IntervalFactory.newFactory();
        Interval lunchInterval = intervalFactory.createIntervalWithMaxDuration(timeProvider.middleOfDay());
        WorkingSchedule nineToFiveSchedule = scheduleFactory.createNineToFiveSchedule(lunchInterval);

        // time passage manager, each iteration will add 1 hour
        TimePassageManager<LocalTime> oneHourTimePassageManager = timePassageManagerFactory.createOneHourTimePassageManager();

        // factors that affect thirsty level
        ThirstyFactorManager thirstyFactorManager = new ThirstyFactorBuilder()
                .addRoomTemperatureCelsius(25)
                .addWorkIntensity(100000)
                .build();

        // working day simulator
        WorkingDaySimulator workingDaySimulator = new WorkingDaySimulator(nineToFiveSchedule, employee, glass,
                                                                          internFactory, thirstyFactorManager,
                                                                          oneHourTimePassageManager);
        workingDaySimulator.simulateWorkingDay();
    }
}
