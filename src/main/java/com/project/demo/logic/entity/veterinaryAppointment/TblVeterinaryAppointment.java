package com.project.demo.logic.entity.veterinaryAppointment;

import com.project.demo.logic.entity.notification.TblNotification;
import com.project.demo.logic.entity.user.TblUser;
import com.project.demo.logic.entity.veterinary.TblVeterinary;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "TBL_Veterinary_Appointment")
public class TblVeterinaryAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Veterinary_Appointment_Id", nullable = false)
    private Long id;

    @Column(name = "Speciallity", nullable = false)
    private String speciallity;

    @Column(name = "Appointment", nullable = false)
    private Instant appointment;

    @Column(name = "State", nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Notification_Id")
    private TblNotification notification;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Veterinary_Id", nullable = false)
    private TblVeterinary veterinary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_Id", nullable = false)
    private TblUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciallity() {
        return speciallity;
    }

    public void setSpeciallity(String speciallity) {
        this.speciallity = speciallity;
    }

    public Instant getAppointment() {
        return appointment;
    }

    public void setAppointment(Instant appointment) {
        this.appointment = appointment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TblNotification getNotification() {
        return notification;
    }

    public void setNotification(TblNotification notification) {
        this.notification = notification;
    }

    public TblVeterinary getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(TblVeterinary veterinary) {
        this.veterinary = veterinary;
    }

    public TblUser getUser() {
        return user;
    }

    public void setUser(TblUser user) {
        this.user = user;
    }

}