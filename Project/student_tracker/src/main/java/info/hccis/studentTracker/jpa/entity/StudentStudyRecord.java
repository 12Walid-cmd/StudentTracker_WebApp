package info.hccis.studentTracker.jpa.entity;

import javax.persistence.*;
import javax.validation.constraints.Min; //  Validation Import
import javax.validation.constraints.NotBlank; // Validation Import
import javax.validation.constraints.NotNull; // Validation Import

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "studentstudylog")
public class StudentStudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Field is Integer (wrapper)

    @NotBlank(message = "Student Name is required.") //  Validation
    @Column(name = "studentName")
    private String studentName;

    @NotBlank(message = "Subject is required.") //  Validation
    @Column(name = "subject")
    private String subject;

    @NotNull(message = "Study Date is required.") //  Validation
    @Column(name = "studyDate")
    private Date studyDate;

    //  FIX: Changed type to Integer (wrapper) to allow null binding from form
    @NotNull(message = "Duration is required.") //  Validation
    @Min(value = 1, message = "Duration must be at least 1 minute.") //  Validation
    @Column(name = "studyDurationMinutes")
    private Integer studyDurationMinutes;

    @Column(name = "studyMethod")
    private String studyMethod;

    //  FIX: Changed type to Integer (wrapper)
    @NotNull(message = "Daily Goal is required.") //  Validation
    @Min(value = 0, message = "Daily Goal cannot be negative.") //  Validation
    @Column(name = "dailyStudyGoal")
    private Integer dailyStudyGoal;

    //  FIX: Changed type to Integer (wrapper)
    @NotNull(message = "Weekly Goal is required.") //  Validation
    @Min(value = 0, message = "Weekly Goal cannot be negative.") //  Validation
    @Column(name = "weeklyStudyGoal")
    private Integer weeklyStudyGoal;

    @Column(name = "notes")
    private String notes;

    @Column(name = "createdDateTime")
    private Timestamp createdDateTime;

    // --- Getters and Setters ---

    //  FIX: Getter must return Integer to match field type and handle null
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; } // Setter should also accept Integer

    // ... (other String/Date getters/setters are fine) ...

    //  FIX: Getters/Setters must now use Integer for numerical fields
    public Integer getStudyDurationMinutes() { return studyDurationMinutes; }
    public void setStudyDurationMinutes(Integer studyDurationMinutes) { this.studyDurationMinutes = studyDurationMinutes; }

    public Integer getDailyStudyGoal() { return dailyStudyGoal; }
    public void setDailyStudyGoal(Integer dailyStudyGoal) { this.dailyStudyGoal = dailyStudyGoal; }

    public Integer getWeeklyStudyGoal() { return weeklyStudyGoal; }
    public void setWeeklyStudyGoal(Integer weeklyStudyGoal) { this.weeklyStudyGoal = weeklyStudyGoal; }

    // ... (rest of getters/setters) ...

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Date getStudyDate() { return studyDate; }
    public void setStudyDate(Date studyDate) { this.studyDate = studyDate; }
    public String getStudyMethod() { return studyMethod; }
    public void setStudyMethod(String studyMethod) { this.studyMethod = studyMethod; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Timestamp getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(Timestamp createdDateTime) { this.createdDateTime = createdDateTime; }
}