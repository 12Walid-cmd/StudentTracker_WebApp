package info.hccis.performance.controllers;

import info.hccis.performance.dao.TicketOrderDAO;
import info.hccis.performance.entity.ReportOrder;
import info.hccis.performance.jpa.entity.TicketOrder;
import info.hccis.performance.repositories.TicketOrderRepository;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to administer reports of the project.
 *
 * @since 20220616
 * @author BJM
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * Send the user to list of reports view.
     *
     * @param model
     * @param session
     * @return To the appropriate view
     * @since 20220624
     * @author BJM
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {

        //BJM 20200602 Issue#1 Set the current date in the session
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    /**
     * Method to send user to the order date report.
     *
     * @param model
     * @return view for list
     * @since 2022-06-20
     * @author BJM
     */
    @RequestMapping("/order/dateoforder")
    public String reportOrderDateOfOrder(Model model) {

        //**********************************************************************
        // Put the ticket order object in the model and send the user
        // to the report input view.
        //**********************************************************************
        model.addAttribute("reportInput", new ReportOrder());
        return "report/reportOrderDate";
    }

    /**
     * Method to send user to the customer name report input.
     *
     * @param model
     * @return view for list
     * @since 2022-06-20
     * @author BJM
     */
    @RequestMapping("/order/customername")
    public String reportOrderCustomerName(Model model) {

        //**********************************************************************
        // Put the ticket order object in the model and send the user
        // to the report input view.
        //**********************************************************************
        model.addAttribute("reportInput", new ReportOrder());
        return "report/reportOrderCustomerName";
    }

    /**
     * Process the report
     *
     * @param model
     * @param reportOrder
     * @return view to show report
     * @since 2022-06-20
     * @author BJM
     */
    @RequestMapping("/order/dateoforder/submit")
    public String reportOrderDateOfOrderSubmit(Model model, @ModelAttribute("reportInput") ReportOrder reportOrder) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you 
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        TicketOrderDAO ticketOrderDAO = new TicketOrderDAO();
        String start = reportOrder.getDateStart();
        String end = reportOrder.getDateEnd();
        ArrayList<TicketOrder> ticketOrders = ticketOrderDAO.selectTicketOrders(start, end);
        reportOrder.setTicketOrders(ticketOrders);

        if (ticketOrders != null && ticketOrders.isEmpty()) {
            model.addAttribute("message", "No data found");
            System.out.println("BJM - no data found");
        }

        model.addAttribute("reportInput", reportOrder);

        return "report/reportOrderDate";
    }

    /**
     * Process the report
     *
     * @param model
     * @param reportOrder
     * @return view to show report
     * @since 2022-06-20
     * @author BJM
     */
    @RequestMapping("/order/customername/submit")
    public String reportOrderCustomerNameSubmit(Model model, @ModelAttribute("reportInput") ReportOrder reportOrder) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you 
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        TicketOrderDAO ticketOrderDAO = new TicketOrderDAO();
        ArrayList<TicketOrder> ticketOrders = ticketOrderDAO.selectTicketOrders(reportOrder.getCustomerName());
        reportOrder.setTicketOrders(ticketOrders);

        if (!ticketOrders.isEmpty()) {
            reportOrder.setCustomerName("");
        } else {
            model.addAttribute("message", "No data found");
            System.out.println("BJM - no data found");
        }

        model.addAttribute("reportInput", reportOrder);

        return "report/reportOrderCustomerName";
    }

}
