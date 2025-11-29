package info.hccis.studentTracker.repositories;

import info.hccis.studentTracker.jpa.entity.StudentStudyRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentTrackerRepository extends CrudRepository<StudentStudyRecord, Integer> {
    /*
    *Use Spring Data JPA functionality to find a list  containing the
    * string passed in as a paramter.
    * @param name The name to find
    * @return The list of items
    * @since 20251030
    * @author Walid
    *
     */
    List<StudentStudyRecord> findAll(); // optional but convenient
    List<StudentStudyRecord> findByStudentNameContaining(String name);

}
