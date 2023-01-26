package org.example;

import io.swagger.models.auth.In;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Invoice {
    private List<InvoiceData> invoiceDataList;
    public Invoice(){}
    public Invoice(List<InvoiceData> invoiceData) {
        super();
        this.invoiceDataList = invoiceData;
    }

    @XmlElement
    public List<InvoiceData> getInvoiceDataList(){
        return this.invoiceDataList;
    }
    public void setInvoiceDataList(List<InvoiceData> invoiceDataList){
        this.invoiceDataList = invoiceDataList;
    }
}
