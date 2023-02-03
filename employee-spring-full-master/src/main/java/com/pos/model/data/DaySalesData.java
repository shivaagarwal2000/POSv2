package com.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class DaySalesData {

    private String date;
    private int orderItemCount;
    private int orderCount;
    private double totalRevenue;
}
