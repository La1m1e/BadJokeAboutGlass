package enterprise.glassjoke.entity.factory;

import enterprise.glassjoke.entity.Intern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory to create new instances of an employee
 *
 * @author hkotsubo
 */
public class InternFactory implements GenericOfficeEntityFactory<Intern> {

    private static final Logger logger = LogManager.getLogger(InternFactory.class.getSimpleName());

    /**
     * Get an instance of this factory.
     *
     * @return The new instance
     */
    @Override
    public GenericOfficeEntityFactory<Intern> newFactory() {
        return new InternFactory();
    }

    /**
     * Create an intern
     *
     * @return The intern
     */
    @Override
    public Intern createEntity() {
        logger.info("Summoning intern...");
        return new Intern();
    }

    /**
     * It should create an intern with the specified name, but actually it always throw an exception because nobody
     * cares about intern's names.
     *
     * @return It always throw an exception because nobody cares about intern's names
     *
     * @throws UnsupportedOperationException - Always throws this exception, as this version (and probably all future
     * ones) doesn't support interns with a name.
     */
    @Override
    public Intern createEntity(String name) {
        throw new UnsupportedOperationException("Currently the system doesn't care about intern's names. "
                + "This is very unlikely to change in the future, and this method is here just to safisfy the compiler.");
    }
}
