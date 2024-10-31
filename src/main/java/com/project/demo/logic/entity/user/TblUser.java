package com.project.demo.logic.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.demo.logic.entity.direction.TblDirection;
import com.project.demo.logic.entity.role.TblRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TBL_User")
public class TblUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;
    
    @Column(name = "Last_Name1", nullable = false)
    private String lastName1;

    @Column(name = "Last_Name2")
    private String lastName2;

    // Remember to change the nullable status to false,
    // per now is true just for testing purposes.
    @Column(name = "Identification", nullable = true)
    private String identification;

    // Remember to change the nullable status to false,
    // per now is true just for testing purposes.
    //CARO: creo que esto debe poder quedar nulo, ya que los compradores no tienen esto (?)
    @Column(name = "VCO", nullable = true)
    private String vco;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Birth_Date")
    private LocalDate birthDate;

    @Column(name = "Phone_Number")
    private String  phoneNumber;

    // Remember to change the nullable status to false,
    // per now is true just for testing purposes.
    @Column(name = "State", nullable = true, length = 2)
    private String state;
/*
    // Remember to change the nullable status to false,
    // per now is true just for testing purposes.
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "Direction_Id", nullable = true)
    private TblDirection direction;*/

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Role_Id", nullable = false)
    private TblRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getTitle().toString());
        return List.of(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
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

    @Override
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
/*
    public TblDirection getDirection() {
        return direction;
    }

    public void setDirection(TblDirection direction) {
        this.direction = direction;
    }*/

    public TblRole getRole() {
        return role;
    }

    public TblUser setRole(TblRole role) {
        this.role = role;
        return this;
    }

}