package info.hccis.studentTracker.soap;

import info.hccis.studentTracker.jpa.entity.BusPass;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface BusPassService {
    @WebMethod
    BusPass getBusPass(int id);
    @WebMethod
    List<BusPass> getBusPasses();
    @WebMethod
    int getCount();
    
}