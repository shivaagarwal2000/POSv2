package org.example.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Invoice")
public class InvoicePojo {

    @Id
    private int orderId;
    @Column(nullable = false)
    private String localFilePath;

}
