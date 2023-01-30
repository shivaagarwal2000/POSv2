package com.increff.employee.pojo;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Orders")
public class OrderPojo extends AbstractDateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(nullable = false)
//    private String orderTime;

    @Column(nullable = false)
    private ZonedDateTime time;

    @Column(nullable = false)
    private String status;

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getOrderTime() {
//        return orderTime;
//    }

//    public void setOrderTime(String orderTime) {
//        this.orderTime = orderTime;
//    }
}
