package info.hccis.bus.pass.jpa.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model for a student's study record.
 * @author Anael
 * @since 20251024
 */
public class StudentStudyRecord {
    private int id;
    private String studentName;
    private String subject;
    private Date studyDate;
    private int studyDurationMinutes;
    private String studyMethod;
    private int dailyStudyGoal;
    private int weeklyStudyGoal;
    private String notes;
    private Timestamp createdDateTime;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Date getStudyDate() { return studyDate; }
    public void setStudyDate(Date studyDate) { this.studyDate = studyDate; }

    public int getStudyDurationMinutes() { return studyDurationMinutes; }
    public void setStudyDurationMinutes(int studyDurationMinutes) { this.studyDurationMinutes = studyDurationMinutes; }

    public String getStudyMethod() { return studyMethod; }
    public void setStudyMethod(String studyMethod) { this.studyMethod = studyMethod; }

    public int getDailyStudyGoal() { return dailyStudyGoal; }
    public void setDailyStudyGoal(int dailyStudyGoal) { this.dailyStudyGoal = dailyStudyGoal; }

    public int getWeeklyStudyGoal() { return weeklyStudyGoal; }
    public void setWeeklyStudyGoal(int weeklyStudyGoal) { this.weeklyStudyGoal = weeklyStudyGoal; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(Timestamp createdDateTime) { this.createdDateTime = createdDateTime; }
}

