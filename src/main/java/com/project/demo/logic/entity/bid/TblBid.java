package com.project.demo.logic.entity.bid;

import com.project.demo.logic.entity.publication.TblPublication;
import com.project.demo.logic.entity.user.TblUser;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Bid")
public class TblBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bid_Id", nullable = false)
    private Long id;

    @Column(name = "Bid_Ammount", nullable = false)
    private Long bidAmmount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "User_Id", nullable = false)
    private TblUser user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Publication_Id", nullable = false)
    private TblPublication publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidAmmount() {
        return bidAmmount;
    }

    public void setBidAmmount(Long bidAmmount) {
        this.bidAmmount = bidAmmount;
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