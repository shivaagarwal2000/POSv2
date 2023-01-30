package org.invoice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Invoice { // TODO Remove used class Priority: 5
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
