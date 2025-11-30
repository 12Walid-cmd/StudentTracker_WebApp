package ca.hccis.studentTracker.api;

/**
 * Simple immutable model class for a single timestamp + temperature record.
 */
public class WeatherRecord {
    private final String time;
    private final double temperature;

    public WeatherRecord(String time, double temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return time + " -> " + temperature + " °C";
    }
}

