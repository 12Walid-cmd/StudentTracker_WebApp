package ca.hccis.studentTracker.entity;


import ca.hccis.studentTracker.util.CisUtility;
import com.google.gson.Gson;

public class StudentTracker {

    private String studentName;// student name
    private String subject; // subject name
    private String studyDate;// study Date completed (yyyy-MM-dd)
    private String studyMethod;
    private String notes;

    private int studyDuration;
    private int dailyStudyGoal;

    private int weeklyStudyGoal;


    /**
     * calculate the Weekly student progress percentage
     *
     * @return weekly study percentage
     * @author WL
     * @since 20250920
     */
    public double calculateWeeklyStudyProgress(){
        double WeeklyStudyProgress = 0;
        double  totalStudyMinutesPerWeek = studyDuration * 7;
        WeeklyStudyProgress = (totalStudyMinutesPerWeek / weeklyStudyGoal)*100;
        return WeeklyStudyProgress;
    }


    public void getInformation() {
         studentName = CisUtility.getInputString("Enter Student Name");
         subject = CisUtility.getInputString("Enter your Subject");
         studyDate = CisUtility.getInputString("Enter your Study Date");
         studyMethod = CisUtility.getInputString("Enter your Study Method");
         notes = CisUtility.getInputString("Enter all Notes");
         dailyStudyGoal = CisUtility.getInputInt("Enter Daily Study Goal");
         weeklyStudyGoal = CisUtility.getInputInt("Enter Weekly Study Goal");

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStudyDuration() {
        return studyDuration;
    }

    public void setStudyDuration(int studyDuration) {
        this.studyDuration = studyDuration;
    }

    public int getDailyStudyGoal() {
        return dailyStudyGoal;
    }

    public void setDailyStudyGoal(int dailyStudyGoal) {
        this.dailyStudyGoal = dailyStudyGoal;
    }

    public int getWeeklyStudyGoal() {
        return weeklyStudyGoal;
    }

    public void setWeeklyStudyGoal(int weeklyStudyGoal) {
        this.weeklyStudyGoal = weeklyStudyGoal;
    }

    // JSON Serialization using Gson
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    @Override
    public String toString() {
        return  "Student:" + studentName +
                "\nSubject: "+ subject +
                "\nstudy Date:" + studyDate +
                "\nStudy method:"+ studyMethod +
                "\n notes:" +notes +
                "\ndaily studyGoal:" +dailyStudyGoal +
                "\n weeklyStudyGoal:" +weeklyStudyGoal;
    }
}
