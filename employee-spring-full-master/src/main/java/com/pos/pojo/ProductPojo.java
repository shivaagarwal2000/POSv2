package com.pos.pojo;

import javax.persistence.*;

@Entity
public class ProductPojo extends AbstractDateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String barcode;
    @Column(nullable = false)
    private int brand_category;//TODO: brandId
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private double mrp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(int brand_category) {
        this.brand_category = brand_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    @Override
    public boolean equals(Object obj) {
        ProductPojo productPojo = (ProductPojo) obj;
        boolean status = false;
        if (this.id == productPojo.id && this.barcode == productPojo.barcode
				&& this.brand_category == productPojo.brand_category && this.name == productPojo.name
				&& this.mrp == productPojo.mrp) {
            status = true;
        }
        return status;
    }

}
