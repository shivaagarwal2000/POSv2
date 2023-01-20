package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//TODO: getter setter
//TODO: all the constraints at db level -- not null, mrp

@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueBrandCategory", columnNames = {"brand", "category"})})
@Entity
public class BrandPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String category;

}
