package com.pos.helper;

import com.pos.model.data.ProductData;
import com.pos.model.forms.BrandForm;
import com.pos.model.forms.ProductForm;
import com.pos.pojo.BrandPojo;

public class DtoTestHelper {

    public static BrandPojo createBrandPojo(String brand, String category){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        return brandPojo;
    }

    public static BrandForm createBrandForm(String brand, String category){
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }

    public static ProductForm createProductForm(String brand, String category, String name, String barcode, Double mrp) {
        ProductForm productForm = new ProductForm();
        productForm.setBrand(brand);
        productForm.setCategory(category);
        productForm.setBarcode(barcode);
        productForm.setMrp(mrp);
        productForm.setName(name);
        return productForm;
    }

}
