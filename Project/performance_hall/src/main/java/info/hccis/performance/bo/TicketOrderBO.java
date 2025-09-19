package info.hccis.performance.bo;

import info.hccis.performance.exception.AllAttributesNeededException;
import info.hccis.performance.jpa.entity.CodeValue;
import info.hccis.performance.jpa.entity.TicketOrder;
import info.hccis.performance.repositories.CodeValueRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Business logic class for ticket purchases
 *
 * @author bjmaclean
 * @since 20220105
 */
public class TicketOrderBO {

    public static final double COST_TICKET = 10;
    public static final double DISCOUNT_HOLLPASS = 0.1;
    public static final double DISCOUNT_VOLUME_10 = 0.1;
    public static final double DISCOUNT_VOLUME_20 = 0.15;

    /**
     * Process all validation
     *
     * @param request
     * @param messageSource
     * @param ticketOrder ticket order
     * @return true or false based on if there were any validation issues
     * @since 20220617
     * @author BJM
     */
    public boolean processValidation(HttpServletRequest request, MessageSource messageSource, TicketOrder ticketOrder) {

        boolean valid = processValidationHollpass(request, messageSource, ticketOrder)
                && processValidationName(request, messageSource, ticketOrder);
        return valid;
    }

    /**
     * Process any business rules validation for the ticket order and set the
     * appropriate messages in the request.- Hollpass number valid? - Name have
 a first and last? - Number of tickets? Client side validation added
     *
     * @param request
     * @param messageSource
     * @param ticketOrder ticket order
     * @return true or false based on if there were any validation issues
     * @since 20220617
     * @author BJM
     */
    public boolean processValidationName(HttpServletRequest request, MessageSource messageSource, TicketOrder ticketOrder) {

        boolean valid;
        //Validate that the name has exactly a first and last name separated by a space.
        try {
            valid = processValidationName(ticketOrder);
        } catch (AllAttributesNeededException aane) {
            valid = false;
            request.setAttribute("errorName", aane.getMessage());
        }
        return valid;
    }

    /**
     * Process any business rules validation for the ticket order a first and
     * last?
     *
     * @param ticketOrder ticket order
     * @return true or false based on if there were any validation issues
     * @throws AllAttributesNeededException
     * @since 20220617
     * @author BJM
     */
    public boolean processValidationName(TicketOrder ticketOrder) throws AllAttributesNeededException {

        boolean valid;
        //Validate that the name has exactly a first and last name separated by a space.
        String name = ticketOrder.getCustomerName();
        name = name.trim();
        boolean nameValid = true;
        String message = "";
        if (name.length() > 0) {
            if (name.indexOf(" ") > 0) {
                if (name.indexOf(" ", name.indexOf(" ") + 1) > 0) {
                    valid = false; //more than one space
                    nameValid = false;
                    message = "Only one space in name expected";
                } else {
                    valid = true;
                }
            } else {
                message = "First and last name required";
                valid = false;
                nameValid = false;
            }
        } else {
            message = "Name not provided";
            nameValid = valid = false;

        }

        if (!nameValid) {
            throw new AllAttributesNeededException(message);
        }
        return valid;
    }

    /**
     * Validate Hollpass number and set the message in request if issues found.
     *
     * @param request Request to set potential messages
     * @param messageSource Object to obtain messaage resources
     * @param ticketOrder ticket order
     * @return true or false based on if there were any validation issues
     * @since 20220617
     * @author BJM
     */
    public boolean processValidationHollpass(HttpServletRequest request, MessageSource messageSource, TicketOrder ticketOrder) {

        boolean valid = true;
        try {
            processValidationHollpass(ticketOrder);
        } catch (AllAttributesNeededException aane) {
            String hollpassValidMessage = messageSource.getMessage("error.message.hollpass", null, LocaleContextHolder.getLocale());
            request.setAttribute("errorHollpass", hollpassValidMessage);
            valid = false;
        }
        return valid;
    }

    /**
     * Apply validation rules for Hollpass number.
     *
     * @param ticketOrder ticket order
     * @return true or false based on if there were any validation issues
     * @throws AllAttributesNeededException
     * @since 20220617
     * @author BJM
     */
    public boolean processValidationHollpass(TicketOrder ticketOrder) throws AllAttributesNeededException {

        boolean valid = true;
        if (ticketOrder.getHollpassNumber() > 0 && !this.validateHollPassNumber(ticketOrder.getHollpassNumber())) {
            throw new AllAttributesNeededException("Hollpass must be a multiple of 13 to be valid");
        }
        return valid;
    }

    /**
     * Calculate and set the cost of the ticket order
     *
     * @param ticketOrder ticket order
     * @return cost of tickets
     * @since 20220617
     * @author BJM
     */
    public TicketOrder processCost(TicketOrder ticketOrder) {

        //Check the Hollpass number.
        boolean validHollpass = true;
        if (!validateHollPassNumber(ticketOrder.getHollpassNumber())) {
            ticketOrder.setHollpassNumber(0);
            validHollpass = false;
        }

        double cost = calculateTicketPrice(ticketOrder.getNumberOfTickets(), validHollpass);
        ticketOrder.setCostOfTickets(new BigDecimal(cost));

        return ticketOrder;
    }

    /**
     * Calculate the cost for a ticket order
     *
     * @param numberOfTickets
     * @param hasHollPass
     * @return cost
     * @since 20220105
     * @author cis2250
     */
    public double calculateTicketPrice(int numberOfTickets, boolean hasHollPass) {
        double discount = 0;
        if (numberOfTickets >= 20) {
            discount += DISCOUNT_VOLUME_20;
        } else if (numberOfTickets >= 10) {
            discount += DISCOUNT_VOLUME_10;
        }

        if (hasHollPass) {
            discount += DISCOUNT_HOLLPASS;
        }

        return numberOfTickets * COST_TICKET * (1 - discount);
    }

    /**
     * Verify if a HollPassNumber if correct Rules: Length is 5 and a multiple
     * of 13
     *
     * @param hollPassNumber
     * @return valid result
     * @since 20220105
     * @author cis2250
     */
    public boolean validateHollPassNumber(int hollPassNumber) {

        boolean valid = false;

        if (hollPassNumber >= 10000 && hollPassNumber < 100000) {
            if (hollPassNumber % 13 == 0) {
                valid = true;
            }
        }

        return valid;
    }
    
    /**
     * Load ticket types using the repository and put them in the session.
     * @author BJM
     * @since 20221024
     */
    public static void loadTicketTypes(HttpSession session, CodeValueRepository _cvr){
                //Get all of the ticket types from the database
        //Ticket Types are code type #2
        if (session.getAttribute("ticketTypes") == null) {
            List<CodeValue> ticketTypes = _cvr.findByCodeTypeId(2);
            System.out.println("We found " + ticketTypes.size() + " ticket types in the database");
            session.setAttribute("ticketTypes", ticketTypes);
        }

    }
    
    
    
}
