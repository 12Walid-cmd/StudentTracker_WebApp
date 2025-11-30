package ca.hccis.studentTracker;



import ca.hccis.studentTracker.threads.Thread1Console;
import ca.hccis.studentTracker.threads.Thread2GUI;

import javax.swing.*;


public class Controller {


    public static void main(String[] args) {
        Thread T1 = new Thread1Console();


        T1.start();

        // Start GUI on EDT
        SwingUtilities.invokeLater(() -> {
            Thread2GUI guiRunnable = new Thread2GUI();
            guiRunnable.run(); // run will itself call invokeLater inside, safe either way
        });


    }


}