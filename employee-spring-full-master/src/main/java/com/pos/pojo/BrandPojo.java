package com.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueBrandCategory", columnNames = {"brand", "category"})})
@Entity
public class BrandPojo extends AbstractDateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String brand;
    @Column(nullable = false, length = 50)
    private String category;
    @Override
    public boolean equals(Object obj){
        BrandPojo brandPojo = (BrandPojo) obj;
        boolean status = false;
        if(this.id == brandPojo.id
                && this.brand == brandPojo.brand && this.category == brandPojo.category){
            status = true;
        }
        return status;
    }
}
