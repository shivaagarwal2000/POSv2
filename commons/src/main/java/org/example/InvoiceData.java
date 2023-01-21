package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceData {

    private int serial;
    private String productName;
    private double mrp;
    private int quantity;
    private double sellingPrice;

}
