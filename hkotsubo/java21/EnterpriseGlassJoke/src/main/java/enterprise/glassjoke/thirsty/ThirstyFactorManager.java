package enterprise.glassjoke.thirsty;

import enterprise.glassjoke.entity.OfficeEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages a list of thirsty factors
 *
 * @author hkotsubo
 */
public class ThirstyFactorManager {

    private static final Logger logger = LogManager.getLogger(ThirstyFactorManager.class.getSimpleName());

    private List<ThirstyFactor> factors;
    private Random rand;

    /**
     * Create a new manager with the specified factors
     *
     * @param factors The list of thirsty factors
     */
    public ThirstyFactorManager(List<ThirstyFactor> factors) {
        this.factors = new ArrayList<>(factors);
        this.rand = new Random();
    }

    /**
     * Create a new manager with no thirsty factors
     */
    public ThirstyFactorManager() {
        this(Collections.emptyList());
    }

    /**
     * Get the list of thirsty factors
     *
     * @return The list of thirsty factors
     */
    public List<ThirstyFactor> getFactors() {
        return factors;
    }

    /**
     * Creates a manager that always return the same thirsty level
     *
     * @param value The value to be returned
     *
     * @return The manager
     */
    public static ThirstyFactorManager createFixedValueManager(int value) {
        return new ThirstyFactorManager() {
            @Override
            public int getThirstyLevel(OfficeEntity entity) {
                return value;
            }

            @Override
            public void changeConditions() {
            }
        };
    }

    /**
     * Get the total thirsty level of all factors combined.
     *
     * @param entity The office entity affected by the factors
     *
     * @return The total thirsty level, which is the amount of liquid to be drank, in milliliters
     */
    public int getThirstyLevel(OfficeEntity entity) {
        int level = 0;
        for (ThirstyFactor factor : factors) {
            level += factor.getThirstyLevel(entity);
        }
        return level;
    }

    /**
     * It might randomically change the conditions of thirsty factors.
     *
     * TODO: currently it's based on random values, future versions might consider other conditions, such as interaction
     * between factors. Example: room temperature affects stress levels (which could be another factor), and it
     * decreases productivity (which could be yet another factor), and so on
     */
    public void changeConditions() {
        for (ThirstyFactor factor : factors) {
            switch (factor) {
                case RoomTemperature roomTemperature ->
                    changeTemperature(roomTemperature);
                case WorkIntensity workIntensity ->
                    changeWorkIntensity(workIntensity);
                default -> {
                }
            }
        }
    }

    private void changeTemperature(RoomTemperature temperature) {
        if (rand.nextBoolean()) {
            double previousTemperature = temperature.getCelsius();
            temperature.setCelsius(previousTemperature + rand.nextInt(-10, 11));
            if (previousTemperature != temperature.getCelsius()) {
                logger.printf(Level.INFO, "Room temperature changed to %.2f celsius", temperature.getCelsius());
            }
        }
    }

    private void changeWorkIntensity(WorkIntensity intensity) {
        if (rand.nextBoolean()) {
            intensity.setIntensity(rand.nextInt(20, 1000) * 100);
            logger.info("Work intensity changed to {}", intensity.getIntensity());
        }
    }
}
