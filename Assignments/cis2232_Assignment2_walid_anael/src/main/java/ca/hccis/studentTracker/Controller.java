package ca.hccis.studentTracker;


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

public class Controller {

    public static final int EXIT = 0;

    public static final String MENU = "1) Add student" + System.lineSeparator()
            + "2) Show student" + System.lineSeparator()
            + EXIT + ") Exit"
            + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";
    public static final String PATH = "c:\\CIS2232\\";
    public static final String FILE_NAME = "data_walid_anael.json";
    private static Path journalPath = null;
    private static FileWriter journalWriter = null;

    public static void main(String[] args) {

        File directory = new File(PATH);
        if (directory.mkdir()) {
            System.out.println("Directory created successfully at: " + PATH);
        } else {
            System.out.println("Failed to create directory. It may already exist.");
        }

        try{
        File myObj = new File("walid_anael.txt"); // Create File object
        if (myObj.createNewFile()) {           // Try to create the file
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace(); // Print error details
    }

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
                    System.out.println(MESSAGE_EXIT);
                    break; //Break out of the loop as we're finished.
                case 1:
                    processAdd();
                    break;
                case 2:
                    processShow();
                    break;
                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }
        } while (menuOption != EXIT);

        try {
            journalWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            journalWriter.write(jsonValue + System.lineSeparator());
            journalWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Processing for menu option 2.
     *
     * @author BJM
     * @since 20201118
     */
    public static void processShow() {
        System.out.println();
        Gson gson = new Gson();
        try {
            List<String> lines = Files.readAllLines(Paths.get(PATH + FILE_NAME));
            if (lines.isEmpty()) {
                System.out.println("No assessments found");
            } else {
                System.out.println("Here are the assessments found");
                for (String current : lines) {
                  StudentTracker studentTracker = gson.fromJson(current, StudentTracker.class);
                    System.out.println(studentTracker.toString());
                    System.out.println();
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        }
    }

}