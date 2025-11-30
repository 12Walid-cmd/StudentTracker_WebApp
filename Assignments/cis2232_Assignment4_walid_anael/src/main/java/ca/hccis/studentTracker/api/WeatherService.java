package ca.hccis.studentTracker.api;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {


    // Default coordinates (Charlottetown area). Change if you want.
    private static final double DEFAULT_LAT = 46.24;
    private static final double DEFAULT_LON = -63.13;


    /**
     * Fetch hourly temperatures from Open-Meteo and return up to maxRecords items.
     */
    public static List<WeatherRecord> fetchHourlyTemperature(int maxRecords) throws Exception {
// build URL as a single continuous string (no embedded newlines)
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&hourly=temperature_2m",
                DEFAULT_LAT, DEFAULT_LON);


        String json = callApi(url);


        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject hourly = root.has("hourly") ? root.getAsJsonObject("hourly") : null;


        if (hourly == null) {
            throw new Exception("API returned no 'hourly' section");
        }


        JsonArray times = hourly.getAsJsonArray("time");
        JsonArray temps = hourly.getAsJsonArray("temperature_2m");


        int available = Math.min(times.size(), temps.size());
        int limit = Math.min(available, Math.max(0, maxRecords));


        List<WeatherRecord> list = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            String t = times.get(i).getAsString();
            double temp = temps.get(i).getAsDouble();
            list.add(new WeatherRecord(t, temp));
        }
        return list;
    }

    private static String callApi(String urlString) throws Exception {
        StringBuilder sb = new StringBuilder();


        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);


        int status = conn.getResponseCode();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                status >= 200 && status < 400 ? conn.getInputStream() : conn.getErrorStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        conn.disconnect();


        if (status < 200 || status >= 300) {
            throw new Exception("HTTP error status: " + status + " response: " + sb.toString());
        }


        return sb.toString();
    }

    // helper used in console mode; keeps console-friendly output
    public static void showHourlyTemperatureConsole(int max) throws Exception {
        List<WeatherRecord> records = fetchHourlyTemperature(max);
        double sum = 0;
        double min = Double.POSITIVE_INFINITY;
        double maxv = Double.NEGATIVE_INFINITY;
        for (WeatherRecord r : records) {
            double t = r.getTemperature();
            sum += t;
            min = Math.min(min, t);
            maxv = Math.max(maxv, t);
            System.out.printf("%s : %.2f °C%n", r.getTime(), t);
        }
        if (!records.isEmpty()) {
            double avg = sum / records.size();
            System.out.println("--------------------------------------");
            System.out.printf("Min: %.2f °C Max: %.2f °C Avg: %.2f °C%n", min, maxv, avg);
        }
    }
}