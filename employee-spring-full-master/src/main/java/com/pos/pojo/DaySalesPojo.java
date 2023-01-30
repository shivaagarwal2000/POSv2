package com.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "pos_day_sales")
public class DaySalesPojo extends AbstractDateAudit{

    @Id
    private ZonedDateTime date;
    @Column(nullable = false)
    private int invoiced_orders_count;
    @Column(nullable = false)
    private int invoiced_items_count;
    @Column(nullable = false)
    private double total_revenue;

}
