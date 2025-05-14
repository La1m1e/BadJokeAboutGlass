package enterprise.glassjoke.container;

import enterprise.glassjoke.exception.InvalidLiquidAmountException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Builder for liquid containers
 *
 * @author hkotsubo
 */
public class LiquidContainerBuilder {

    // capacity in milliliters (how much liquid can be put in this container)
    private int capacity;

    // the type of this liquid container
    private LiquidContainerType type;

    // the contents of this container
    private final Map<LiquidType, Integer> contents;

    public LiquidContainerBuilder() {
        this.capacity = 0;
        this.type = null;
        this.contents = new TreeMap<>();
    }

    /**
     * Set the capacity of the liquid container, in milliliters
     *
     * @param capacity The capacity in milliliters
     *
     * @return This builder
     */
    public LiquidContainerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Set the liquid container type
     *
     * @param type The liquid container type
     *
     * @return This builder
     */
    public LiquidContainerBuilder withType(LiquidContainerType type) {
        this.type = Objects.requireNonNull(type, "type can't be null");
        return this;
    }

    /**
     * Set the contents of the liquid container.
     *
     * If the sum of all amounts exceeds the capacity, it'll throw an exception only when trying to build the liquid
     * container.
     *
     * @param contents A map that... maps each liquid type with its respective amount in milliliters
     *
     * @return This builder
     */
    public LiquidContainerBuilder withContents(Map<LiquidType, Integer> contents) {
        this.contents.putAll(Objects.requireNonNull(contents, "contens can't be null"));
        return this;
    }

    /**
     * Add an amount of liquid type.
     *
     * If the sum of all amounts exceeds the capacity, it'll throw an exception only when trying to build the liquid
     * container.
     *
     * @param type The liquid type
     * @param amount The amount in milliliters
     *
     * @return This builder
     */
    public LiquidContainerBuilder addContent(LiquidType type, int amount) {
        if (amount <= 0) {
            throw new InvalidLiquidAmountException("Amount of " + type + " must be positive");
        }

        this.contents.put(Objects.requireNonNull(type, "liquid type can't be null"), this.contents.getOrDefault(type, 0) + amount);
        return this;
    }

    /**
     * Build the liquid container
     *
     * @return The liquid container
     */
    public LiquidContainer build() {
        return new LiquidContainer(this.capacity, this.contents, this.type);
    }
}
