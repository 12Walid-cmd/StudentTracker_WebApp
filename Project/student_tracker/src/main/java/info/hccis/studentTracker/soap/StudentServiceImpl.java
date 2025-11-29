package info.hccis.studentTracker.soap;


import info.hccis.studentTracker.dao.StudentTrackerDAO;
import info.hccis.studentTracker.jpa.entity.StudentStudyRecord;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "info.hccis.studentTracker.soap.StudentService")
public class StudentServiceImpl implements StudentService {

    @Override
    public List<StudentStudyRecord> getStudentRepo(){

        StudentTrackerDAO studentDAO = new StudentTrackerDAO();
        ArrayList<StudentStudyRecord> studentrepo = studentDAO.selectAll();
        return studentrepo;
    }

}
