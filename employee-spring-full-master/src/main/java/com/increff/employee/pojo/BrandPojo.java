package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//TODO: getter setter

@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueBrandCategory", columnNames = {"brand", "category"})})
@Entity
public class BrandPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String brand;
    @Column(nullable = false, length = 50)
    private String category;

}
