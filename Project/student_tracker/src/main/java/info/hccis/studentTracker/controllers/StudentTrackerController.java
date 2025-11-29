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

    @RequestMapping("/submit")
    public String submit(Model model,
            @Valid @ModelAttribute("record") StudentStudyRecord record,
            BindingResult bindingResult) {

        // --- 1. Custom Business Validation (Place your own logic here) ---
        boolean valid = true;

        // Example of custom validation (currently doing nothing but keeping the structure)
        // if (record.getStudyDurationMinutes() < 10) {
        //     valid = false;
        // }
        // You can add your own checks here and handle the error messages manually

        // --- 2. Check for Validation Errors (JPA annotations and custom logic) ---
        if (!valid || bindingResult.hasErrors()) {

            // Print errors to the console (for debugging, like the teacher's code)
            System.out.println("--------------------------------------------");
            System.out.println("Validation error - Study Tracker");
            bindingResult.getAllErrors().forEach(error ->
                    System.out.println(error.getObjectName() + " - " + error.getDefaultMessage())
            );
            System.out.println("--------------------------------------------");

            // The object already contains the submitted data and validation errors.
            // Return to the 'add' template so the user can see the errors and fix them.
            model.addAttribute("record", record);
            return "studenttracker/add";
        }

        // --- 3. Process and Save (Only runs if validation passes) ---

        // Set the created date/time only if it's a NEW record (ID is null)
        // You may need to import java.sql.Timestamp
        if (record.getId() == null || record.getId() == 0) {
            record.setCreatedDateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        }

        // Save the record to the database
        repo.save(record);

        // Redirect back to the list view
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
//    @PostMapping("/report")
//    public String processReport(@RequestParam("studentName") String studentName, Model model) throws IOException {
//
//        // 1️ Get report data from DAO
//        ArrayList<StudentStudyRecord> records = dao.selectByStudentName(studentName);
//
//        // 2️ Add to model for display
//        model.addAttribute("records", records);
//        model.addAttribute("studentName", studentName);
//
//        // 3️ Write report to file
//        writeReportToFile(records, studentName);
//
//        // ⃣ Return the results view
//        return "studenttracker/reportResults";
//    }
//    @PostMapping("/report")
//    public String generateReport(@RequestParam String studentName, Model model) throws IOException {
//        // Example: Fetch data from database
//        ArrayList<StudentStudyRecord> records = dao.selectByStudentName(studentName);
//
//        if (records.isEmpty()) {
//            model.addAttribute("message", "No study records found for " + studentName);
//        } else {
//            writeReportToFile(records, studentName);
//            model.addAttribute("records", records);
//        }
//
//        // Return same view to show results below form
//        return "report/reportStudentTrackerName";
//    }

    /*
    *
    *@param model
    * @author Walid
    * @since 2025-11-14
    *
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