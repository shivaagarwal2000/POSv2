package com.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class InventoryPojo extends AbstractDateAudit{

    @Id
    private int id;
    @Column(nullable = false)
    private int quantity;
    @Override
    public boolean equals(Object obj){
        InventoryPojo inventoryPojo = (InventoryPojo) obj;
        boolean status = false;
        if(this.id == inventoryPojo.id
                && this.quantity == inventoryPojo.quantity){
            status = true;
        }
        return status;
    }

}
