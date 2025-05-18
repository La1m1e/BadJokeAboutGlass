package enterprise.glassjoke.container;

/**
 * Enum for different types of liquid
 *
 * @author hkotsubo
 */
public enum LiquidType {
    WATER("water"), COFFEE("coffee"), TEA("tea"), MILK("milk");

    private final String description;

    private LiquidType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
