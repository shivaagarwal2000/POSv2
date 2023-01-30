package org.invoice.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Invoice") // TODO Table name usually goes as project_table_pojo (snakecase) ex: pos_order_pojo Priority: 5
// TODO Add custom naming generation strategy in DbConfig for pos, for invoice we can keep the name invoice_pojo without strategy
public class InvoicePojo {

    @Id
    private int orderId;
    @Column(nullable = false)
    private String localFilePath;

}
