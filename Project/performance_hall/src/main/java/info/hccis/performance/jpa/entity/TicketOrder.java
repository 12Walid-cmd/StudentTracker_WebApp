/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.hccis.performance.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bjmaclean
 */
@Entity
@Table(name = "ticketorder")
@XmlRootElement
public class TicketOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "customerName")
    private String customerName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hollpassNumber")
    private int hollpassNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dateOfOrder")
    private String dateOfOrder;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dateOfPerformance")
    private String dateOfPerformance;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "timeOfPerformance")
    private String timeOfPerformance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numberOfTickets")
    private int numberOfTickets;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ticketTypeCode")
    private int ticketTypeCode;

    //Note this is transient and the repository wont try to save it to the database.
    @Transient
    private String ticketTypeCodeDescription;

    public String getTicketTypeCodeDescription() {
        return ticketTypeCodeDescription;
    }

    public void setTicketTypeCodeDescription(String ticketTypeCodeDescription) {
        this.ticketTypeCodeDescription = ticketTypeCodeDescription;
    }


    

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "costOfTickets")
    private BigDecimal costOfTickets;

    public TicketOrder() {
    }

    public TicketOrder(Integer id) {
        this.id = id;
    }

    public TicketOrder(Integer id, String customerName, int hollpassNumber, String dateOfOrder, String dateOfPerformance, String timeOfPerformance, int numberOfTickets, int ticketTypeCode, BigDecimal costOfTickets) {
        this.id = id;
        this.customerName = customerName;
        this.hollpassNumber = hollpassNumber;
        this.dateOfOrder = dateOfOrder;
        this.dateOfPerformance = dateOfPerformance;
        this.timeOfPerformance = timeOfPerformance;
        this.numberOfTickets = numberOfTickets;
        this.ticketTypeCode = ticketTypeCode;
        this.costOfTickets = costOfTickets;
    }

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

    public int getHollpassNumber() {
        return hollpassNumber;
    }

    public void setHollpassNumber(int hollpassNumber) {
        this.hollpassNumber = hollpassNumber;
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

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getTicketTypeCode() {
        return ticketTypeCode;
    }

    public void setTicketTypeCode(int ticketTypeCode) {
        this.ticketTypeCode = ticketTypeCode;
    }

    public BigDecimal getCostOfTickets() {
        return costOfTickets;
    }

    public void setCostOfTickets(BigDecimal costOfTickets) {
        this.costOfTickets = costOfTickets;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketOrder)) {
            return false;
        }
        TicketOrder other = (TicketOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.hccis.performance.jpa.entity.TicketOrder[ id=" + id + " ]";
    }
    
}
