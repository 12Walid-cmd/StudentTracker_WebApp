package info.hccis.studentTracker.soap;

import info.hccis.studentTracker.jpa.entity.StudentStudyRecord;
import info.hccis.studentTracker.repositories.StudentTrackerRepository;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface StudentService {
    @WebMethod
    List<StudentStudyRecord> getStudentRepo();
}
