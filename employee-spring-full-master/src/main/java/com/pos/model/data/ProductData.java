package com.pos.model.data;

import com.pos.model.forms.ProductForm;

public class ProductData extends ProductForm {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        ProductData productData = (ProductData) obj;
        boolean status = false;
        if (this.id == productData.id
                && this.getBarcode() == productData.getBarcode() && Double.compare(this.getMrp(), productData.getMrp()) == 0
                && this.getName() == productData.getName() && this.getBrand() == productData.getBrand() && this.getCategory() == productData.getCategory()) {
            status = true;
        }
        return status;
    }

}
