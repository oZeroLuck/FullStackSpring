package com.testservice.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.testservice.webapp.validator.PlateConstraint;
import com.testservice.webapp.validator.RegistrationYearConstraint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "licence_plate", unique = true)
    @PlateConstraint
    private String licencePlate;

    @Column(name = "model")
    @NotNull
    private String model;

    @Column(name = "brand")
    @NotNull
    private String brand;

    @Column(name = "year_of_registration")
    @RegistrationYearConstraint
    private int registrationYear;

    @OneToMany(mappedBy = "theVehicle", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Reservation> reservation;

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public Vehicle(String type, String licencePlate, String model, String brand, int registrationYear) {
        this.type = type.toUpperCase();
        this.licencePlate = licencePlate.toUpperCase();
        this.model = model.toUpperCase();
        this.brand = brand.toUpperCase();
        this.registrationYear = registrationYear;
    }

    public Vehicle() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(int regYear) {
        this.registrationYear = regYear;
    }
}

/*

 */
