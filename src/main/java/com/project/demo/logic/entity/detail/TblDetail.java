package com.project.demo.logic.entity.detail;

import com.project.demo.logic.entity.transaction.TblTransaction;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Detail")
public class TblDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Detail_Id", nullable = false)
    private Long id;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Price", nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Transaction_Id", nullable = false)
    private TblTransaction transaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public TblTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(TblTransaction transaction) {
        this.transaction = transaction;
    }

}