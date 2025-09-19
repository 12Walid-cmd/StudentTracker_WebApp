package info.hccis.performance.controllers;

import info.hccis.performance.bo.TicketOrderBO;
import info.hccis.performance.jpa.entity.CodeValue;
import info.hccis.performance.jpa.entity.TicketOrder;
import info.hccis.performance.repositories.CodeValueRepository;
import info.hccis.performance.repositories.TicketOrderRepository;
import info.hccis.performance.util.CisUtility;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.glassfish.jersey.internal.guava.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to administer ticket orders. Note that the code was taken from
 * Fred Campos' project from 2021 which also had modifications from Ferhad in
 * 2022.
 *
 * @since 20220616
 * @author BJM
 */
@Controller
@RequestMapping("/ticketorder")
public class TicketOrderController {

    private final TicketOrderRepository _tor;
    private final CodeValueRepository _cvr;

    @Autowired
    public TicketOrderController(TicketOrderRepository tor, CodeValueRepository cvr) {
        _tor = tor;
        _cvr = cvr;
    }

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(TicketOrderController.class);

    @RequestMapping("")
    public String home(Model model, HttpSession session) {

        TicketOrder ticketOrder = new TicketOrder();

        //BJM testing
//        List<TicketOrder> theListTest = _tor.findByCustomerNameContaining("Joe");

        model.addAttribute("ticketOrder", ticketOrder);
        System.out.println("There are " + _tor.count() + " ticket orders in the database");
        
        Iterable<TicketOrder> theTickets = _tor.findAll();
        loadTicketTypeDescriptions(theTickets);
        
        model.addAttribute("ticketOrders", _tor.findAll());
        return "ticketorder/list";
    }

    private void loadTicketTypeDescriptions(Iterable<TicketOrder> theTickets){
        
        List<TicketOrder> result = Lists.newArrayList(theTickets);
        
        for(TicketOrder current: theTickets){
            Optional<CodeValue> codeValue = _cvr.findById(current.getTicketTypeCode());
            if(codeValue.isPresent()){
                CodeValue currentCodeValue = codeValue.get();
                current.setTicketTypeCodeDescription(currentCodeValue.getEnglishDescription());
            }else{
                current.setTicketTypeCodeDescription("Unknown");
            }
     
        }
    }
    
    /**
     * Page to add new ticket order. Taken from tutor app from 2022 (which was
     * also derived from class samples)
     *
     * @param model
     * @return add
     * @since 2022-06-16
     * @author BJM
     */
    @RequestMapping("/add")
    public String add(Model model, HttpSession session) {

        TicketOrderBO.loadTicketTypes(session, _cvr);
        
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setCostOfTickets(new BigDecimal(0));
        ticketOrder.setDateOfOrder(CisUtility.getCurrentDate("yyyy-MM-dd"));
        ticketOrder.setNumberOfTickets(1); //default to 1

        model.addAttribute("ticketOrder", ticketOrder);
        return "ticketorder/add";
    }

    /**
     * Search for tickets given a customer name
     *
     * @param model
     * @param ticketOrder
     * @return view for list
     * @since 2022-06-20
     * @author BJM
     */
    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute("ticketOrder") TicketOrder ticketOrder) {

        //**********************************************************************
        //Use repository method created to find any ticket orders which contain 
        //the name entered on the list page.
        //**********************************************************************
        model.addAttribute("ticketOrders", _tor.findByCustomerNameContaining(ticketOrder.getCustomerName()));
        logger.debug("Customer found");
        return "ticketorder/list";
    }

    /**
     * Submit method that processes add and edit and any form submission
     *
     * @param model
     * @param request
     * @param ticketOrder Order being added or modified
     * @param bindingResult Result of SQL
     * @return add with errors or ticketOrder
     * @since 20220616
     * @author CIS2232
     */
    @RequestMapping("/submit")
    public String submit(Model model, HttpServletRequest request, @Valid @ModelAttribute("ticketOrder") TicketOrder ticketOrder, BindingResult bindingResult) {

        TicketOrderBO ticketOrderBO = new TicketOrderBO();

        boolean valid = ticketOrderBO.processValidation(request, messageSource, ticketOrder);

        if (!valid || bindingResult.hasErrors()) {
            System.out.println("--------------------------------------------");
            System.out.println("Validation error - BJM");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + "-" + error.toString() + "-" + error.getDefaultMessage());
            }
            System.out.println("--------------------------------------------");

            return "ticketorder/add";
        }

        ticketOrderBO.processCost(ticketOrder);
        _tor.save(ticketOrder);
        return "redirect:/ticketorder";
    }

    /**
     * Page to edit ticket order
     *
     * @param id ID of ticket order
     * @param model
     * @return add if ticketOrder is valid, otherwise, tickerOrder
     * @since 20220616
     * @author Fred Campos
     * @modified 20220616 BJM Using similar approach for ticket orders
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session) {

        TicketOrderBO.loadTicketTypes(session, _cvr);

        Optional ticketOrder = _tor.findById(id);
        if (ticketOrder.isPresent()) {
            model.addAttribute("ticketOrder", ticketOrder.get());
            return "ticketorder/add";
        }
        return "redirect:/ticketorder";
    }

    /**
     * Page to delete an order
     *
     * @param id ID of ticket order
     * @return ticket order
     * @since 20220616
     * @author BJM
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        _tor.deleteById(id);
        return "redirect:/ticketorder";
    }

}
