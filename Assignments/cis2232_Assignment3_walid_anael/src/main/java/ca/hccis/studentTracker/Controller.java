package ca.hccis.studentTracker;



import ca.hccis.studentTracker.threads.Thread1Console;
import ca.hccis.studentTracker.threads.Thread2GUI;


public class Controller {


    public static void main(String[] args) {
        Thread T1 = new Thread1Console();
        Thread T2 = new Thread(new Thread2GUI());


        T1.start();
        T2.start();

    }


}