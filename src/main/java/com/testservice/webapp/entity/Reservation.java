package com.testservice.webapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name="vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle theVehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name="customer_id", nullable = false)
    private WebUser theCustomer;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date endDate;

    @Column(name = "status")
    @NotNull
    private String status = "Pending";

    public  Reservation() { }

    public Reservation(Vehicle theVehicle, Date startDate, Date endDate, WebUser theCustomer) {
        this.theVehicle = theVehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theCustomer = theCustomer;
    }

    public String getStatus() {return status; }

    public void setStatus(String approved) {this.status = approved; }

    public Vehicle getTheVehicle() {
        return theVehicle;
    }

    public void setTheVehicle(Vehicle theVehicle) {
        this.theVehicle = theVehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WebUser getTheCustomer() {
        return theCustomer;
    }

    public void setTheCustomer(WebUser theCustomer) {
        this.theCustomer = theCustomer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
