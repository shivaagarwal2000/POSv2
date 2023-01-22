package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pos_day_sales")
public class DaySalesPojo {

    @Id
    private String date;
    @Column(nullable = false)
    private int invoiced_orders_count;
    @Column(nullable = false)
    private int invoiced_items_count;
    @Column(nullable = false)
    private double total_revenue;

}
