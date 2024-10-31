package com.project.demo.logic.entity.photo;

import com.project.demo.logic.entity.publication.TblPublication;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_Photo")
public class TblPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Photo_Id", nullable = false)
    private Long id;

    @Column(name = "Url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Publication_Id")
    private TblPublication publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TblPublication getPublication() {
        return publication;
    }

    public void setPublication(TblPublication publication) {
        this.publication = publication;
    }

}