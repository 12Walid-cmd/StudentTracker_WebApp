package info.hccis.studentTracker;

import info.hccis.studentTracker.rest.StudentService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    @Autowired
    private StudentService studentService; // Spring-managed

    @PostConstruct
    private void init() {
        register(studentService); // register the actual Spring bean
    }
}


