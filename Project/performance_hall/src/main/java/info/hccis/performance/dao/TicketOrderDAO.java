package info.hccis.performance.dao;

import info.hccis.performance.jpa.entity.TicketOrder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO class to access ticket orders.
 *
 * @author bjmaclean
 * @since 20220621
 */
public class TicketOrderDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(TicketOrderDAO.class);

    public TicketOrderDAO() {

        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error(e.toString());
        }

    }

    /**
     * Select ticket orders in a given date range.
     *
     * @since 20220621
     * @author BJM
     */
    public ArrayList<TicketOrder> selectTicketOrders(String start, String end) {
        PreparedStatement stmt;
        ArrayList<TicketOrder> ticketOrders = new ArrayList();
        try {
            String query = "SELECT * FROM TicketOrder ticketorder "
                    + "WHERE ticketorder.dateOfOrder > ? "
                    + "and ticketorder.dateOfOrder < ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, start);
            stmt.setString(2, end);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                TicketOrder ticketOrder = new TicketOrder();
                ticketOrder.setId(rs.getInt("id"));
                ticketOrder.setCustomerName(rs.getString("customerName"));
                ticketOrder.setDateOfOrder(rs.getString("dateOfOrder"));
                ticketOrder.setDateOfPerformance(rs.getString("dateOfPerformance"));
                ticketOrder.setTimeOfPerformance(rs.getString("timeOfPerformance"));
                ticketOrder.setNumberOfTickets(rs.getInt("numberOfTickets"));
                ticketOrder.setHollpassNumber(rs.getInt("hollpassNumber"));
                ticketOrder.setCostOfTickets(rs.getBigDecimal("costOfTickets"));
                ticketOrders.add(ticketOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Found orders:  " + ticketOrders.size());
        return ticketOrders;
    }

    /**
     * Select ticket orders for a given customer by name
     *
     * @since 20220621
     * @author BJM
     */
    public ArrayList<TicketOrder> selectTicketOrders(String customerName) {
        PreparedStatement stmt;
        ArrayList<TicketOrder> ticketOrders = new ArrayList();
        
        //https://stackoverflow.com/questions/2857164/cannot-use-a-like-query-in-a-jdbc-preparedstatement
        //Bitbucket Issue#5
        String customerNameLike = "%"+customerName+"%";
        try {
            String query = "SELECT * FROM TicketOrder ticketorder "
                    + "WHERE customerName LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, customerNameLike);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                TicketOrder ticketOrder = new TicketOrder();
                
                ticketOrder.setId(rs.getInt("id"));
                ticketOrder.setCustomerName(rs.getString("customerName"));
                ticketOrder.setDateOfOrder(rs.getString("dateOfOrder"));
                ticketOrder.setDateOfPerformance(rs.getString("dateOfPerformance"));
                ticketOrder.setTimeOfPerformance(rs.getString("timeOfPerformance"));
                ticketOrder.setNumberOfTickets(rs.getInt("numberOfTickets"));
                ticketOrder.setHollpassNumber(rs.getInt("hollpassNumber"));
                ticketOrder.setCostOfTickets(rs.getBigDecimal("costOfTickets"));
                ticketOrders.add(ticketOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Found orders:  " + ticketOrders.size());
        return ticketOrders;
    }

}
