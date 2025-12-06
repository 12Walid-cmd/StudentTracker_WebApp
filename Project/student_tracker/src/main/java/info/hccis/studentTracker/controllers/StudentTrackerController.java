package info.hccis.studentTracker.controllers;

//import info.hccis.bus.pass.model.StudentStudyRecord;
import info.hccis.studentTracker.jpa.entity.StudentStudyRecord;
import info.hccis.studentTracker.repositories.StudentTrackerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;


//@RequestMapping("/studenttracker")
//public class StudentTrackerController {
//
//    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerController.class);
//    private final StudentTrackerDAO dao = new StudentTrackerDAO();
//
//    /**
//     * Default page – show all records
//     */
//    @RequestMapping("")
//    public String home(Model model) {
//        ArrayList<StudentStudyRecord> records = dao.selectAll();
//        model.addAttribute("records", records);
//        model.addAttribute("record", new StudentStudyRecord());
//        return "studenttracker/list";
//    }

/**
 * Controller to administer CRUD for Student Study Records.
 *
 * @author Walid
 * @since 20251112
 */
@Controller
@RequestMapping("/studenttracker")
public class StudentTrackerController {

    private final StudentTrackerRepository repo;

    @Autowired
    public StudentTrackerController(StudentTrackerRepository repo) {
        this.repo = repo;
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentTrackerController.class);


    /**
     * Default page – List all records
     */
    // In StudentTrackerController.java

    @RequestMapping("")
    public String home(Model model, @RequestParam(required = false) String keyword) {

        Iterable<StudentStudyRecord> records;

        // Check if the keyword is present and not just whitespace
        if (keyword != null && !keyword.trim().isEmpty()) {

            // Use the new method: findByStudentNameContaining(String name)
            // This is a case-sensitive search by default.
            records = repo.findByStudentNameContaining(keyword);

            // Add the keyword back to the model so the search box remains populated
            model.addAttribute("keyword", keyword);

        } else {
            // Default: Find all records if no keyword is provided
            records = repo.findAll();
        }

        model.addAttribute("records", records);
        model.addAttribute("record", new StudentStudyRecord());

        return "studenttracker/list";
    }

    // Inside your StudentTrackerController.java

    @PostMapping("/submit")
    public String submit(
            @Valid @ModelAttribute("record") StudentStudyRecord record,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // OPTIONAL: add a global form message
            model.addAttribute("formError", "Please correct the highlighted errors");

            // IMPORTANT: keep the same object so errors stay bound
            model.addAttribute("record", record);

            return "studenttracker/add";
        }

        if (record.getId() == null || record.getId() == 0) {
            record.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
        }

        repo.save(record);

        return "redirect:/studenttracker";
    }


    /**
     * Show input form for report
     */
    @RequestMapping("/report/student/tracker/name")
    public String showReportInputForm(Model model) {
        model.addAttribute("record", new StudentStudyRecord());
        return "report/reportStudentTrackerName"; // HTML form view
    }

    /**
     * Process report (generate + display + file output)
     */

    @RequestMapping("/add")
    public String add(Model model ) {
        StudentStudyRecord record2 = new StudentStudyRecord();
        model.addAttribute("record", record2);
        return "studenttracker/add";
    }

    /**
     * Show all student study records (list page)
//     */
//    @GetMapping("")
//    public String list(Model model) {
//        model.addAttribute("records", dao.selectAll());
//        model.addAttribute("record", new StudentStudyRecord());
//        return "studenttracker/list"; // y list page (not report)
//    }

    /**
     * Page to delete a bus pass
     *
     * @param id ID
     * @return redirect to the list page
     * @author Walid
     * @since 20251114
     */

    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {

        try {
            repo.deleteById(id);
            model.addAttribute("messageSuccess", "Student record has been deleted successfully");
        }catch (Exception e) {
            model.addAttribute("messageError", e.getMessage());
        }
        Iterable<StudentStudyRecord> records = repo.findAll();
        model.addAttribute("records", records);
        model.addAttribute("record", new StudentStudyRecord());
        return "studenttracker/list";
    }

    /**
     * Page to edit
     *
     * @param id  ID
     * @param model
     * @author BJM
     * @since 20241025
     */

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id , HttpSession session) {

        Optional<StudentStudyRecord> students = repo.findById(id); // Use generic for clarity
        if(students.isPresent()) {
            model.addAttribute("record", students.get());

            // FIX: Use the shared add form
            return "studenttracker/add";
        }

        model.addAttribute("MessageError", "student not found");
        Iterable<StudentStudyRecord> records = repo.findAll();
        model.addAttribute("records", records);
        model.addAttribute("record", new StudentStudyRecord());

        // FIX: If not found, redirect to list
        return "studenttracker/list";
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
                        "Subject: %s | Date: %s | Duration: %d mins | Method: %s | Notes: %s | Success Rate: %.2f%%\\n\"",
                        record.getSubject(),
                        record.getStudyDate(),
                        record.getStudyDurationMinutes(),
                        record.getStudyMethod(),
                        record.getNotes(),
                        record.getSuccessRate()
                ));
            }

            writer.write("====================================\n");
            writer.write("Total Records: " + records.size() + "\n");

        }
    }
    @PostMapping("/report")
    public String generateReport(
            @RequestParam String studentName,
            Model model
    ) {
        // Search records
        ArrayList<StudentStudyRecord> records =
                new ArrayList<>(repo.findByStudentNameContaining(studentName));

        // If no student found
        if (records.isEmpty()) {
            model.addAttribute("message", "No records found for student: " + studentName);
            model.addAttribute("records", null);
            return "report/reportStudentTrackerName";
        }

        // If records found
        model.addAttribute("records", records);
        model.addAttribute("studentName", studentName);

//        // OPTIONAL: Write to file
//        try {
//            writeReportToFile(records, studentName);
//        } catch (Exception e) {
//            model.addAttribute("message", "File could not be written: " + e.getMessage());
//        }

        return "report/reportStudentTrackerName";
    }

}