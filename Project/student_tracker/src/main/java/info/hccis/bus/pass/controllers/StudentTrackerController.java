package info.hccis.bus.pass.controllers;

import info.hccis.bus.pass.dao.StudentTrackerDAO;
//import info.hccis.bus.pass.model.StudentStudyRecord;
import info.hccis.bus.pass.jpa.entity.StudentStudyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
@RequestMapping("/studenttracker")
public class StudentTrackerController {

    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerController.class);
    private final StudentTrackerDAO dao = new StudentTrackerDAO();

    /**
     * Default page – show all records
     */
    @RequestMapping("")
    public String home(Model model) {
        ArrayList<StudentStudyRecord> records = dao.selectAll();
        model.addAttribute("records", records);
        model.addAttribute("record", new StudentStudyRecord());
        return "studenttracker/list";
    }

    /**
     * Show input form for report
     */
    @RequestMapping("/report")
    public String showReportInputForm(Model model) {
        model.addAttribute("record", new StudentStudyRecord());
        return "studenttracker/reportStudentTrackerName"; // HTML form view
    }

    /**
     * Process report (generate + display + file output)
     */
    @PostMapping("/report")
    public String processReport(@RequestParam("studentName") String studentName, Model model) throws IOException {

        // 1️ Get report data from DAO
        ArrayList<StudentStudyRecord> records = dao.selectByStudentName(studentName);

        // 2️ Add to model for display
        model.addAttribute("records", records);
        model.addAttribute("studentName", studentName);

        // 3️ Write report to file
        writeReportToFile(records, studentName);

        // ⃣ Return the results view
        return "studenttracker/reportResults";
    }

    /**
     * Helper to write report to a file
     */
    private void writeReportToFile(ArrayList<StudentStudyRecord> records, String studentName) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String timestamp = LocalDateTime.now().format(formatter);
        String fileName = "C:\\cis2232\\StudentReport_" + studentName + "_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("STUDENT STUDY REPORT\n");
            writer.write("Generated: " + LocalDateTime.now() + "\n");
            writer.write("Student: " + studentName + "\n");
            writer.write("====================================\n");

            for (StudentStudyRecord record : records) {
                writer.write(String.format(
                        "Subject: %s | Date: %s | Duration: %d mins | Method: %s | Notes: %s\n",
                        record.getSubject(),
                        record.getStudyDate(),
                        record.getStudyDurationMinutes(),
                        record.getStudyMethod(),
                        record.getNotes()
                ));
            }

            writer.write("====================================\n");
            writer.write("Total Records: " + records.size() + "\n");

        }
    }
}