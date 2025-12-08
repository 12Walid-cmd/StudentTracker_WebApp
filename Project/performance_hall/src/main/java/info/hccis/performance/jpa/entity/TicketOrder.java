package info.hccis.performance.jpa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "ticketorder")
public class TicketOrder {

    //Note this is transient and the repository wont try to save it to the database.
    @Transient
    private String ticketTypeCodeDescription;

    public String getTicketTypeCodeDescription() {
        return ticketTypeCodeDescription;
    }

    public void setTicketTypeCodeDescription(String ticketTypeCodeDescription) {
        this.ticketTypeCodeDescription = ticketTypeCodeDescription;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "customerName", nullable = false, length = 20)
    private String customerName;

    @NotNull
    @Column(name = "hollpassNumber", nullable = false)
    private Integer hollpassNumber;

    @Size(max = 4)
    @Column(name = "discountCode", length = 4)
    private String discountCode;

    @Size(max = 10)
    @NotNull
    @Column(name = "dateOfOrder", nullable = false, length = 10)
    private String dateOfOrder;

    @Size(max = 10)
    @NotNull
    @Column(name = "dateOfPerformance", nullable = false, length = 10)
    private String dateOfPerformance;

    @Size(max = 5)
    @NotNull
    @Column(name = "timeOfPerformance", nullable = false, length = 5)
    private String timeOfPerformance;

    @NotNull
    @Column(name = "numberOfTickets", nullable = false)
    private Integer numberOfTickets;

    @NotNull
    @Column(name = "ticketTypeCode", nullable = false)
    private Integer ticketTypeCode;

    @NotNull
    @Column(name = "costOfTickets", nullable = false, precision = 6, scale = 2)
    private BigDecimal costOfTickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getHollpassNumber() {
        return hollpassNumber;
    }

    public void setHollpassNumber(Integer hollpassNumber) {
        this.hollpassNumber = hollpassNumber;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getDateOfPerformance() {
        return dateOfPerformance;
    }

    public void setDateOfPerformance(String dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }

    public String getTimeOfPerformance() {
        return timeOfPerformance;
    }

    public void setTimeOfPerformance(String timeOfPerformance) {
        this.timeOfPerformance = timeOfPerformance;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Integer getTicketTypeCode() {
        return ticketTypeCode;
    }

    public void setTicketTypeCode(Integer ticketTypeCode) {
        this.ticketTypeCode = ticketTypeCode;
    }

    public BigDecimal getCostOfTickets() {
        return costOfTickets;
    }

    public void setCostOfTickets(BigDecimal costOfTickets) {
        this.costOfTickets = costOfTickets;
    }

}