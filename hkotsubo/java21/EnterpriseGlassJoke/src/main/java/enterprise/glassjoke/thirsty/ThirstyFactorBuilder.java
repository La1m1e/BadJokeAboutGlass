package enterprise.glassjoke.thirsty;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a list of thirsty factors
 *
 * @author hkotsubo
 */
public class ThirstyFactorBuilder {

    // it can have just one of each type
    private RoomTemperature roomTemperature;
    private WorkIntensity workIntensity;

    /**
     * Creates a new builder
     */
    public ThirstyFactorBuilder() {
        this.roomTemperature = null;
        this.workIntensity = null;
    }

    /**
     * Add room temperature
     *
     * @param celsius The temprerature in celsius
     *
     * @return This builder
     */
    public ThirstyFactorBuilder addRoomTemperatureCelsius(double celsius) {
        this.roomTemperature = RoomTemperature.ofCelsius(celsius);
        return this;
    }

    /**
     * Add room temperature
     *
     * @param fahrenheit The temprerature in fahrenheit
     *
     * @return This builder
     */
    public ThirstyFactorBuilder addRoomTemperatureFahrenheit(double fahrenheit) {
        this.roomTemperature = RoomTemperature.ofFahrenheit(fahrenheit);
        return this;
    }

    /**
     * Add work intensity
     *
     * @param intensity The intensity
     *
     * @return This builder
     */
    public ThirstyFactorBuilder addWorkIntensity(int intensity) {
        this.workIntensity = new WorkIntensity(intensity);
        return this;
    }

    /**
     * Build a thirsty factor manager
     *
     * @return The thirsty factor manager
     */
    public ThirstyFactorManager build() {
        List<ThirstyFactor> list = new ArrayList<>();
        if (this.roomTemperature != null) {
            list.add(this.roomTemperature);
        }
        if (this.workIntensity != null) {
            list.add(this.workIntensity);
        }
        return new ThirstyFactorManager(list);
    }
}
