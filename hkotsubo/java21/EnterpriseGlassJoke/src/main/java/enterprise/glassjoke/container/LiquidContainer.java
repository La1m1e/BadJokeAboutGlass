package enterprise.glassjoke.container;

import enterprise.glassjoke.entity.OfficeEntity;
import enterprise.glassjoke.exception.InvalidLiquidAmountException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generic liquid container.
 *
 * The contents are stored as a map: the keys are the liquid types, and the values are the respective amounts of that
 * type.
 *
 * @author hkotsubo
 */
public class LiquidContainer {

    // maximum value that can be used as container capacity
    private static final int MAX_VALID_CAPACITY;

    static {
        int maxCapacity;
        try {
            maxCapacity = Integer.parseInt(System.getProperty("enterprise.glassjoke.container.max_capacity"));
        } catch (Exception e) {
            maxCapacity = -1;
        }
        if (maxCapacity < 0) {
            MAX_VALID_CAPACITY = 1000;
        } else {
            MAX_VALID_CAPACITY = maxCapacity;
        }
    }

    // capacity in milliliters (how much liquid can be put in this container)
    protected int capacity;

    // current volume of liquid inside the container
    protected int currentVolume;

    // the type of this liquid container
    protected LiquidContainerType type;

    // the contents of this container
    protected Map<LiquidType, Integer> contents;

    protected Logger logger;

    /**
     * Creates a container with the specified capacity and type.
     *
     * If the capacity is negative or greater than the maximum value, it throws an InvalidLiquidAmountException.
     *
     * The maximum value can be changed if you set the system property "enterprise.glassjoke.container.max_capacity".
     * The value must fit in an integer. If the property is not set, or is set to an invalid integer value, the maximum
     * value will be 1000 (1 liter).
     *
     * The container will start empty (current volume = zero).
     *
     * @param capacity The capacity in milliliters
     * @param type The container type
     */
    public LiquidContainer(int capacity, LiquidContainerType type) {
        if (capacity < 0) {
            throw new InvalidLiquidAmountException("Capacity can't be negative");
        }
        if (capacity > MAX_VALID_CAPACITY) {
            throw new InvalidLiquidAmountException("Capacity can't be greater than " + MAX_VALID_CAPACITY);
        }
        this.capacity = capacity;
        this.currentVolume = 0; // container starts empty
        this.contents = new TreeMap<>();
        this.type = Objects.requireNonNull(type, "Type can't be null");
        this.logger = LogManager.getLogger("LiquidContainer(" + type.toString() + ")");
    }

    /**
     * Creates a container with the specified capacity, contents and type.
     *
     * If the capacity is negative or greater than the maximum value, it throws an InvalidLiquidAmountException.
     *
     * The maximum value can be changed if you set the system property "enterprise.glassjoke.container.max_capacity".
     * The value must fit in an integer. If the property is not set, or is set to an invalid integer value, the maximum
     * value will be 1000 (1 liter).
     *
     * If the contens is null, a NullPointerException is thrown. If any of the values isn't positive, it throws an
     * InvalidLiquidAmountException.
     *
     * @param capacity The capacity in milliliters
     * @param contents Map with the liquid types and their respective volumes
     * @param type The container type
     */
    public LiquidContainer(int capacity, Map<LiquidType, Integer> contents, LiquidContainerType type) {
        this(capacity, type);
        this.contents = Objects.requireNonNull(contents, "Contens can't be null");
        this.currentVolume = 0;
        for (LiquidType liquidType : this.contents.keySet()) {
            int vol = this.contents.get(liquidType);
            if (vol <= 0) {
                throw new InvalidLiquidAmountException("Amount of " + liquidType + " must be positive");
            }
            this.currentVolume += vol;
            if (this.currentVolume > this.capacity) {
                throw new InvalidLiquidAmountException("Sum of contents volume is greater than the capacity");
            }
        }
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public LiquidContainerType getType() {
        return type;
    }

    public Map<LiquidType, Integer> getContents() {
        // return defensive copy
        return Collections.unmodifiableMap(this.contents);
    }

    /**
     * Drink some milliliters from this container
     *
     * If the amount is greater or equal the container's current volume, it drinks all the container's contents.
     *
     * If the amount is negative, it throws an InvalidLiquidAmountException.
     *
     * Otherwise, all the liquid types are drank in order (considering the key order of the contents map), until the
     * amount is consumed or the container is empty.
     *
     * TODO: maybe it could be changed to consume all liquids equally, and the remainder to be consumed by the liquid
     * with the greatest amount.
     *
     * @param amountMilliLiters Amount of milliliters to consume
     * @param consumer Who is drinking from this container
     */
    public void consume(int amountMilliLiters, OfficeEntity consumer) {
        if (amountMilliLiters < 0) {
            throw new InvalidLiquidAmountException("Amount to dring can't be negative");
        }

        if (amountMilliLiters >= this.currentVolume) {
            if (amountMilliLiters != this.currentVolume) {
                logger.info("{} {} wants to drink {}ml but the {} has only {}ml. Drinking everything...",
                            consumer.getClass().getSimpleName(), consumer.getName(), amountMilliLiters,
                            this.type, this.currentVolume);
            } else {
                logger.info("{} {} is drinking the whole {} ({}ml)", consumer.getClass().getSimpleName(), consumer.getName(),
                            this.type, amountMilliLiters);
            }
            this.contents.clear();
            this.currentVolume = 0;
            return;
            // TODO: maybe it should call the intern to refill and drink the remaining amount
            // Example: if there's 100ml but wants to drink 150ml, it should drink everything, calls intern to refill and drink more 50ml
        }

        int consumed = 0;
        Iterator<LiquidType> iterator = this.contents.keySet().iterator();
        while (iterator.hasNext()) {
            LiquidType liquidType = iterator.next();
            int currentAmount = this.contents.get(liquidType);
            int amountToConsume = Math.min(currentAmount, amountMilliLiters);
            logger.info("{} {} is drinking {}ml of {}", consumer.getClass().getSimpleName(), consumer.getName(),
                        amountToConsume, liquidType);
            if (currentAmount == amountToConsume) {
                iterator.remove();
            } else {
                this.contents.put(liquidType, currentAmount - amountToConsume);
            }
            consumed += amountToConsume;
            amountMilliLiters -= amountToConsume;
        }
        this.currentVolume -= consumed;
        consumer.setThirsty(false);
    }

    public boolean isEmpty() {
        return this.currentVolume == 0;
    }

    /**
     * Fill the container with a liquid.
     *
     * The liquid will be added until the container is totally full (current volume equals capacity).
     *
     * @param type The liquid type to be added
     */
    public void fill(LiquidType type) {
        type = Objects.requireNonNull(type, "Type must not be null");
        int amount = this.capacity - this.currentVolume;
        this.contents.put(type, amount + this.contents.getOrDefault(type, 0));
        this.currentVolume = this.capacity;
    }
}
