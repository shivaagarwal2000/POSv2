package com.pos.helper;

import com.pos.model.data.InventoryData;
import com.pos.model.data.ProductData;
import com.pos.model.forms.BrandForm;
import com.pos.model.forms.InventoryForm;
import com.pos.model.forms.ProductForm;
import com.pos.pojo.BrandPojo;

import java.util.Objects;

public class DtoTestHelper {

    public static BrandPojo createBrandPojo(String brand, String category) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        return brandPojo;
    }

    public static BrandForm createBrandForm(String brand, String category) {
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
        if (!Objects.isNull(mrp)) productForm.setMrp(mrp);
        productForm.setName(name);
        return productForm;
    }

    public static ProductData createProductData(String brand, String category, String name, String barcode, Double mrp, int id) {
        ProductData productData = new ProductData();
        productData.setBrand(brand);
        productData.setCategory(category);
        productData.setBarcode(barcode);
        productData.setMrp(mrp);
        productData.setName(name);
        productData.setId(id);
        return productData;
    }

    public static InventoryForm createInventoryForm(String barcode, Integer quantity) {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(barcode);
        inventoryForm.setQuantity(quantity);
        return inventoryForm;
    }

    public static InventoryData createInventoryData(String barcode, Integer quantity, int id) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(id);
        inventoryData.setBarcode(barcode);
        inventoryData.setQuantity(quantity);
        return inventoryData;
    }

}
