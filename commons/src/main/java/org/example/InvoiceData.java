package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceData {

    private int serial;// TODO What is the use of serial? Priority: 5
    private String productName;
    private double mrp;
    private int quantity;
    private double sellingPrice;

}
