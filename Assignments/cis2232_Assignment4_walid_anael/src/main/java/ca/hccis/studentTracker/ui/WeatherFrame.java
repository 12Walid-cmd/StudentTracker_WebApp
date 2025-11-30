package ca.hccis.studentTracker.ui;


import ca.hccis.studentTracker.api.WeatherRecord;
import ca.hccis.studentTracker.api.WeatherService;


import javax.swing.*;
import java.awt.*;
import java.util.List;


public class WeatherFrame extends JFrame {


    private final JTextArea textArea = new JTextArea(20, 60);
    private final JButton refreshButton = new JButton("Refresh");
    private final JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 240, 1));


    public WeatherFrame() {
        super("Weather Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea.setEditable(false);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Count:"));
        top.add(countSpinner);
        top.add(refreshButton);


        refreshButton.addActionListener(e -> loadData());


        add(top, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
    /**
     * Loads data in a background thread (so EDT is not blocked).
     */
    public void loadData() {
        refreshButton.setEnabled(false);
        textArea.setText("Loading...");
        int count = (Integer) countSpinner.getValue();


        new Thread(() -> {
            try {
                List<WeatherRecord> recs = WeatherService.fetchHourlyTemperature(count);


                StringBuilder sb = new StringBuilder();
                double sum = 0;
                double min = Double.POSITIVE_INFINITY;
                double max = Double.NEGATIVE_INFINITY;


                for (WeatherRecord r : recs) {
                    sb.append(String.format("%s : %.2f °C\n", r.getTime(), r.getTemperature()));
                    double t = r.getTemperature();
                    sum += t;
                    min = Math.min(min, t);
                    max = Math.max(max, t);
                }


                final String out = sb.toString();
                final double fmin = min, fmax = max, favg = recs.isEmpty() ? 0 : sum / recs.size();


                SwingUtilities.invokeLater(() -> {
                    textArea.setText(out + String.format("\nMin: %.2f °C Max: %.2f °C Avg: %.2f °C", fmin, fmax, favg));
                    refreshButton.setEnabled(true);
                });


            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    textArea.setText("Error loading data: " + ex.getMessage());
                    refreshButton.setEnabled(true);
                });
            }
        }).start();
    }
}