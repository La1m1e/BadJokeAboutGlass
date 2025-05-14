package enterprise.glassjoke.entity;

import enterprise.glassjoke.container.LiquidContainer;
import enterprise.glassjoke.container.LiquidType;
import enterprise.glassjoke.entity.factory.InternFactory;
import org.apache.logging.log4j.LogManager;

/**
 * An office employee
 *
 * @author hkotsubo
 */
public class Employee extends OfficeEntity {

    /**
     * Create an employee without the default name.
     *
     * Use this when you don't care about the employee's name (e.g. when you're just a number for the company).
     *
     * If the system property "enterprise.glassjoke.entity.default_entity_name" is set, it'll be used as the default
     * name. Otherwise, the name will be set to "unknown".
     *
     */
    public Employee() {
        super(OfficeEntityType.EMPLOYEE);
        this.thirsty = true;
        setLogger();
    }

    /**
     * Create an employee with a name
     *
     * @param name The employee's name
     */
    public Employee(String name) {
        super(name, OfficeEntityType.EMPLOYEE);
        this.thirsty = true;
        setLogger();
    }

    private void setLogger() {
        this.logger = LogManager.getLogger("Employee(" + this.name + ")");
    }

    @Override
    public void work() {
        // TODO: future versions will hopefully add an implementation. For now, just prints a message to pretend I'm working
        logger.info("Working");
    }

    @Override
    public void fill(LiquidContainer container, LiquidType liquidType) {
        throw new UnsupportedOperationException("In the current version (and probably in all future ones), employees "
                + "don't fill their own liquid containers. They always call an intern to do it for them.");
    }

    @Override
    public void enjoyInterval() {
        // TODO: future versions will hopefully add an implementation with many different ways to enjoy the interval.
        // For now, just prints a message
        logger.info("enjoying interval");
    }

    public Intern callIntern(InternFactory internFactory) {
        logger.info("Glass is empty, where's the intern?");
        return internFactory.createEntity();
    }
}
