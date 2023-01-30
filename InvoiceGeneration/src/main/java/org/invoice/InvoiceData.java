package org.invoice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvoiceData {// TODO Remove unused class Priority: 5
    private int serial;
    private String productName;
    private int quantity;
    private double mrp;
    private double sellingPrice;

    public InvoiceData(){}
    public InvoiceData(int serial, String productName, int quantity, double mrp, double sellingPrice) {
        super();
        this.serial = serial;
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.sellingPrice = sellingPrice;
    }
    @XmlElement
    public int getSerial() {
        return serial;
    }
    public void setSerial(int serial) {
        this.serial = serial;
    }
    @XmlElement
    public String getProductName() {
        return productName;
    }
    public void setName(String productName) {
        this.productName = productName;
    }
    @XmlElement
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @XmlElement
    public double getMrp () {
        return mrp;
    }
    public void setMrp(double mrp) {
        this.mrp = mrp;
    }
    @XmlElement
    public double getSellingPrice () {
        return sellingPrice;
    }
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
