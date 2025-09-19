package info.hccis.performance.entity;

import info.hccis.performance.jpa.entity.TicketOrder;
import java.util.ArrayList;

/**
 * Entity class to hold the attributes of the order related reports.
 * @author bjmaclean
 * @since 20220621
 */
public class ReportOrder {
    private String dateStart;
    private String dateEnd;
    private String customerName;
    private ArrayList<TicketOrder> ticketOrders;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    
    
    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ArrayList<TicketOrder> getTicketOrders() {
        return ticketOrders;
    }

    public void setTicketOrders(ArrayList<TicketOrder> ticketOrders) {
        this.ticketOrders = ticketOrders;
    }
    
    
}
