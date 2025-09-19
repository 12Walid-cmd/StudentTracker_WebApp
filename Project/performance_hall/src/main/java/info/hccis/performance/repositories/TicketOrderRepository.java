package info.hccis.performance.repositories;

import info.hccis.performance.jpa.entity.TicketOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrderRepository extends CrudRepository<TicketOrder, Integer> {
    
    /**
     * Use Spring Data JPA functionality to find a list of customers containing the
     * string passed in as a paramter.
     * 
     * @param name The name to find
     * @return The list of customers
     * @since 20220624 
     * @author BJM
     */
    //https://www.baeldung.com/spring-jpa-like-queries
    List<TicketOrder> findByCustomerNameContaining(String name);
    List<TicketOrder> findByCustomerName(String name);
    List<TicketOrder> findByDateOfOrder(String dateOfOrder);
}