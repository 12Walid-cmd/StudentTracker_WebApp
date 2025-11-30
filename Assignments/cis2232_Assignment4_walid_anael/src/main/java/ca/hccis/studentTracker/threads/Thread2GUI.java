package ca.hccis.studentTracker.threads;

import ca.hccis.studentTracker.ui.WeatherFrame;

import javax.swing.SwingUtilities;

public class Thread2GUI implements Runnable {

    @Override
    public void run() {
        // ALWAYS start Swing on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            WeatherFrame frame = new WeatherFrame();
            frame.setVisible(true);

            // Optional: auto-load weather data on startup
            frame.loadData();
        });
    }
}
