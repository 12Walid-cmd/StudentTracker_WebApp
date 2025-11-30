package ca.hccis.studentTracker.threads;

import ca.hccis.studentTracker.api.WeatherService;
import ca.hccis.studentTracker.entity.StudentTracker;
import ca.hccis.studentTracker.util.CisUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Thread1Console extends Thread {

    public static final int EXIT = 0;

    public static final String MENU = "1) Add student" + System.lineSeparator()
            + "2) Show student" + System.lineSeparator()
            + "3) Weather (API) - Hourly temperatures" + System.lineSeparator()
            + EXIT + ") Exit"
            + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";
    public static final String PATH = "c:\\CIS2232\\";
    public static final String FILE_NAME = "data_walid_anael.json";
    private static Path journalPath = null;
    private static FileWriter journalWriter = null;

    private static CisUtility cisUtility = null;
    // REMOVED: private static int totalEntries = 0;


    public void run(){
        // No gui for this thread
        cisUtility = new CisUtility();
        cisUtility.setIsGUI(false);


        // Create new file if it doesn't already exist
        journalPath = Paths.get(PATH + FILE_NAME);
        if (!Files.exists(journalPath)) {
            File journalFile = new File(journalPath.toString());
        }
        try {
            journalWriter = new FileWriter(PATH + FILE_NAME, true);
        } catch (IOException e) {
            System.out.println("Error creating file writer");
            throw new RuntimeException(e);
        }

        int menuOption;

        do {
            menuOption = CisUtility.getInputInt(MENU);

            switch (menuOption) {
                case EXIT:
                    // Synchronize closing the writer to prevent conflicts
                    try {
                        synchronized (Thread1Console.class) {
                            if (journalWriter != null) {
                                journalWriter.close();
                                journalWriter = null;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error closing file writer");
                    }
                    System.out.println(MESSAGE_EXIT);
                    break; //Break out of the loop as we're finished.
                case 1:
                    processAdd();
                    break;
                case 2:
                    processShow();
                    break;
                case 3:
                    processWeatherApi();
                    break;
                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }
        } while (menuOption != EXIT);
    }


    /**
     * Call the WeatherService API and display results in the console.
     *
     * @author Walid
     * @since 20251129
     */

    private void processWeatherApi() {
        System.out.println("Processing option 3 - Weather (API)");

        try {
            int max = CisUtility.getInputInt("How many hourly records to show (e.g. 12): ");

            // Call your API service (make sure WeatherService exists in ca.hccis.studentTracker.api)
            WeatherService.showHourlyTemperatureConsole(max);

        } catch (Exception e) {
            System.out.println("Error while fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Add journal entry from user and save to the file
     *
     * @author Walid
     * @since 20251909
     */
    public static void processAdd() {
        System.out.println("Processing option 1");

        StudentTracker studentTracker= new StudentTracker();
        studentTracker.getInformation();


        // Insert new entry into journal.txt and catch possible errors
        try {
            String jsonValue = studentTracker.toJson();
            System.out.println(jsonValue);

            // CRITICAL SECTION: Synchronize access to the shared file resource
            // Using the class object as a temporary lock for this file
            synchronized (Thread1Console.class) {
                journalWriter.write(jsonValue + System.lineSeparator());
                journalWriter.flush();
            }
            // REMOVED: totalEntries++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processShow() {
        System.out.println();
        Gson gson = new Gson();
        try {
            List<String> lines;
            // CRITICAL SECTION: Synchronize file read access to ensure consistency
            synchronized (Thread1Console.class) {
                lines = Files.readAllLines(Paths.get(PATH + FILE_NAME));
            }

            int currentTotalEntries = lines.size();

            if (lines.isEmpty()) {
                System.out.println("No assessments found");
            } else {
                System.out.println("Here are the assessments found");
                for (String current : lines) {
                    StudentTracker studentTracker = gson.fromJson(current, StudentTracker.class);
                    System.out.println(studentTracker.toString());
                    // Use the calculated size for the total entries, not the unreliable static counter
                    System.out.println("The total entries is: " + currentTotalEntries );
                    System.out.println();
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        }
    }

    // REMOVED: public static int getTotalEntries()
}