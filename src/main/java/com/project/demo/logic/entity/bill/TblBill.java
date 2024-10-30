package com.project.demo.logic.entity.bill;

import com.project.demo.logic.entity.detail.TblDetail;
import com.project.demo.logic.entity.tax.TblTax;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Bill")
public class TblBill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Bill_Id", nullable = false)
    private Long id;

    @Column(name = "Subtotal", nullable = false)
    private Long subtotal;

    @Column(name = "Total", nullable = false)
    private Long total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Tax_Id", nullable = false)
    private TblTax tax;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Detail_Id", nullable = false)
    private TblDetail detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public TblTax getTax() {
        return tax;
    }

    public void setTax(TblTax tax) {
        this.tax = tax;
    }

    public TblDetail getDetail() {
        return detail;
    }

    public void setDetail(TblDetail detail) {
        this.detail = detail;
    }

}