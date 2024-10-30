package com.project.demo.logic.entity.transaction;

import com.project.demo.logic.entity.publication.TblPublication;
import com.project.demo.logic.entity.user.TblUser;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Transaction")
public class TblTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Transaction_Id", nullable = false)
    private Long id;

    @Column(name = "Status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_Id", nullable = false)
    private TblUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Publication_Id", nullable = false)
    private TblPublication publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TblUser getUser() {
        return user;
    }

    public void setUser(TblUser user) {
        this.user = user;
    }

    public TblPublication getPublication() {
        return publication;
    }

    public void setPublication(TblPublication publication) {
        this.publication = publication;
    }

}