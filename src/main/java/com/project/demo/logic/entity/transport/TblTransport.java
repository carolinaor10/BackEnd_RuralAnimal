package com.project.demo.logic.entity.transport;

import com.project.demo.logic.entity.bill.TblBill;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Transport")
public class TblTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Transport_Id", nullable = false)
    private Long id;

    @Column(name = "State", nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Bill_Id", nullable = false)
    private TblBill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TblBill getBill() {
        return bill;
    }

    public void setBill(TblBill bill) {
        this.bill = bill;
    }

}