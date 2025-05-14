package enterprise.glassjoke.container;

/**
 * Enum for different types of liquid container
 *
 * @author hkotsubo
 */
public enum LiquidContainerType {
    // TODO: add support for more types in the future
    GLASS("glass"), MUG("mug");

    private final String description;

    private LiquidContainerType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
