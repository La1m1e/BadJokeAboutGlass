package enterprise.glassjoke.entity;

import enterprise.glassjoke.container.LiquidContainer;
import enterprise.glassjoke.container.LiquidType;
import enterprise.glassjoke.exception.NobodyCaresAboutYourNameException;
import enterprise.glassjoke.thirsty.ThirstyFactorManager;
import org.apache.logging.log4j.LogManager;

/**
 * An intern
 *
 * @author hkotsubo
 */
public class Intern extends OfficeEntity {

    /**
     * Create an intern with a default name.
     *
     * In most cases - if not always - nobody cares about the intern's name, so it's always set to "intern".
     *
     */
    public Intern() {
        super("intern", OfficeEntityType.INTERN);
        this.logger = LogManager.getLogger("intern");
    }

    @Override
    public void work() {
        // TODO: future versions will hopefully add an implementation. For now, just prints a message to pretend I'm working
        logger.info("working");
    }

    @Override
    public void fill(LiquidContainer container, LiquidType liquidType) {
        logger.info("Filling {} with {}", container.getType(), liquidType);
        container.fill(liquidType);
    }

    @Override
    public void drink(LiquidContainer container, ThirstyFactorManager thirstyFactorManager) {
        // interns are allowed to drink a limited amount of liquid, regardless of the thirsty factors
        super.drink(container, ThirstyFactorManager.createFixedValueManager(50));
    }

    @Override
    public String getName() {
        throw new NobodyCaresAboutYourNameException("Are you the new intern? Cool, tell me your name so I can forget it after 2 seconds.");
    }

    @Override
    public void enjoyInterval() {
        throw new UnsupportedOperationException("Interns don't have intervals");
    }
}
