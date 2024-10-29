package com.project.demo.logic.entity.user;

import com.project.demo.logic.entity.direction.TblDirection;
import com.project.demo.logic.entity.role.TblRole;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_User")
public class TblUser {
    @Id
    @Column(name = "User_Id", nullable = false)
    private Long id;

    @Column(name = "Last_Name1", nullable = false)
    private String lastName1;

    @Column(name = "Last_Name2")
    private String lastName2;

    @Column(name = "Identification", nullable = false)
    private String identification;

    @Column(name = "VCO", nullable = false)
    private String vco;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Birth_Date")
    private LocalDate birthDate;

    @Column(name = "Phone_Number")
    private String phoneNumber;

    @Column(name = "State", nullable = false, length = 2)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Direction_Id", nullable = false)
    private TblDirection direction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Role_Id", nullable = false)
    private TblRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getVco() {
        return vco;
    }

    public void setVco(String vco) {
        this.vco = vco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TblDirection getDirection() {
        return direction;
    }

    public void setDirection(TblDirection direction) {
        this.direction = direction;
    }

    public TblRole getRole() {
        return role;
    }

    public void setRole(TblRole role) {
        this.role = role;
    }

}