package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class InventoryPojo {

    @Id
    private int id;
    @Column(nullable = false)
    private int quantity;

}
