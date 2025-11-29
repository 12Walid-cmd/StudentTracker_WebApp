package info.hccis.studentTracker.rest;

import info.hccis.studentTracker.jpa.entity.StudentStudyRecord;
import info.hccis.studentTracker.repositories.StudentTrackerRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Optional;

/**
 * REST service for StudentStudyRecord.
 * Works with Jersey registerClasses() style and Spring DI.
 */
@Path("/StudentService/v1/students")
@Component // Spring manages it
public class StudentService {

    private final StudentTrackerRepository studentRepo;
    private final Gson gson = new Gson();

    @Autowired
    public StudentService(StudentTrackerRepository studentRepo) {
        this.studentRepo = studentRepo;
    }


    /** GET all students */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        ArrayList<StudentStudyRecord> students = (ArrayList<StudentStudyRecord>) studentRepo.findAll();
        if (students.isEmpty()) {
            return Response.status(HttpURLConnection.HTTP_NO_CONTENT).build();
        }
        return Response.ok(students).build();
    }

    /** GET one student by ID */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") Integer id) {
        Optional<StudentStudyRecord> opt = studentRepo.findById(id);
        if (!opt.isPresent()) {
            return Response.status(HttpURLConnection.HTTP_NOT_FOUND).build();
        }
        return Response.ok(opt.get()).build();
    }

    /** DELETE a student by ID */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            Optional<StudentStudyRecord> opt = studentRepo.findById(id);
            if (!opt.isPresent()) {
                return Response.status(HttpURLConnection.HTTP_NOT_FOUND).build();
            }
            studentRepo.delete(opt.get());
            return Response.status(HttpURLConnection.HTTP_OK)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        } catch (Exception e) {
            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /** POST - create a new student */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String jsonIn) {
        try {
            String resultJson = save(jsonIn);
            return Response.status(HttpURLConnection.HTTP_OK)
                    .entity(resultJson)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        } catch (IllegalArgumentException ex) {
            return Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /** PUT - update existing student */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") int id, String jsonIn) {
        try {
            Optional<StudentStudyRecord> existing = studentRepo.findById(id);
            if (!existing.isPresent()) {
                return Response.status(HttpURLConnection.HTTP_NOT_FOUND).build();
            }
            String resultJson = save(jsonIn);
            return Response.status(HttpURLConnection.HTTP_OK)
                    .entity(resultJson)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        } catch (IllegalArgumentException ex) {
            return Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    /** Helper: parse JSON, validate, save */
    private String save(String json) throws IllegalArgumentException {
        StudentStudyRecord student = gson.fromJson(json, StudentStudyRecord.class);

        // Validation
        if (student.getStudentName() == null || student.getStudentName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (student.getSubject() == null || student.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Subject is required.");
        }

        // If ID is null, treat as new
        if (student.getId() == null) {
            student.setId(0);
        }

        student = studentRepo.save(student);
        return gson.toJson(student);
    }
}
