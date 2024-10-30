package com.project.demo.logic.entity.direction;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Direction")
public class TblDirection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Direction_Id", nullable = false)
    private Long id;

    @Column(name = "Province", nullable = false)
    private String province;

    @Column(name = "Canton", nullable = false)
    private String canton;

    @Column(name = "District", nullable = false)
    private String district;

    @Column(name = "Other_Details", nullable = false)
    private String otherDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

}