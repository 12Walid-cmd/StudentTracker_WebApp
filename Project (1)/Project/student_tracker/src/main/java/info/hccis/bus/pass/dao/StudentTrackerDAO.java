
package info.hccis.bus.pass.dao;

import info.hccis.bus.pass.jpa.entity.StudentStudyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * DAO class to access the Student Tracker database.
 *
 * Handles JDBC operations for StudentStudyRecord table.
 * @author Anael
 * @since 20251024
 */
public class StudentTrackerDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerDAO.class);

    //=========================
    // Constructor
    //=========================
    public StudentTrackerDAO() {
        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
            logger.info(" Database connected successfully to " + connectionString);
        } catch (SQLException e) {
            logger.error(" Database connection failed: " + e.getMessage());
        }
    }

    //=========================
    // SELECT ALL
    //=========================
    public ArrayList<StudentStudyRecord> selectAll() {
        ArrayList<StudentStudyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM StudentStudyLog";

        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
            records = loadList(rs);
        } catch (SQLException e) {
            logger.error("Error selecting all records: " + e.getMessage());
        }

        return records;
    }

    //=========================
    // SELECT BY STUDENT NAME
    //=========================
    public ArrayList<StudentStudyRecord> selectByStudentName(String studentName) {
        ArrayList<StudentStudyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM StudentStudyLog WHERE studentName = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentName);
            rs = ps.executeQuery();
            records = loadList(rs);
        } catch (SQLException e) {
            logger.error("Error selecting by student name: " + e.getMessage());
        }

        return records;
    }

    //=========================
    // SELECT BY DATE RANGE
    //=========================
    public ArrayList<StudentStudyRecord> selectByDateRange(Date startDate, Date endDate) {
        ArrayList<StudentStudyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM StudentStudyLog WHERE studyDate BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            records = loadList(rs);
        } catch (SQLException e) {
            logger.error("Error selecting by date range: " + e.getMessage());
        }

        return records;
    }

    //=========================
    // LOAD LIST FROM RESULTSET
    //=========================
    private ArrayList<StudentStudyRecord> loadList(ResultSet rs) throws SQLException {
        ArrayList<StudentStudyRecord> records = new ArrayList<>();

        while (rs.next()) {
            StudentStudyRecord record = new StudentStudyRecord();
            record.setId(rs.getInt("id"));
            record.setStudentName(rs.getString("studentName"));
            record.setSubject(rs.getString("subject"));
            record.setStudyDate(rs.getDate("studyDate"));
            record.setStudyDurationMinutes(rs.getInt("studyDurationMinutes"));
            record.setStudyMethod(rs.getString("studyMethod"));
            record.setDailyStudyGoal(rs.getInt("dailystudyGoal"));
            record.setWeeklyStudyGoal(rs.getInt("weeklystudyGoal"));
            record.setNotes(rs.getString("notes"));
            record.setCreatedDateTime(rs.getTimestamp("createdDateTime"));

            records.add(record);
        }

        return records;
    }

}
