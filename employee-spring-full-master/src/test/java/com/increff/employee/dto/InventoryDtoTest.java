package com.increff.employee.dto;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.forms.InventoryForm;
import com.increff.employee.model.forms.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventoryDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testAdd() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        System.out.println(brandDao);
        brandDao.insert(brandPojo);
        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("barcode");
        productForm.setMrp(1);
        productForm.setName("name");
        productDto.add(productForm);
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(productForm.getBarcode());
        inventoryForm.setQuantity(3);
        inventoryDto.add(inventoryForm);
        ProductPojo productPojo = productDao.select(productForm.getBarcode());
        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test
    public void testAddWithoutBarcode(){

    }

    @Test
    public void testAddWithoutQuantity(){

    }

    @Test
    public void testAddWithoutProduct(){

    }

    @Test
    public void testGetById() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        System.out.println(brandDao);
        brandDao.insert(brandPojo);
        ProductForm productForm = new ProductForm();
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productForm.setBarcode("barcode");
        productForm.setMrp(1);
        productForm.setName("name");
        productDto.add(productForm);
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(productForm.getBarcode());
        inventoryForm.setQuantity(3);
        inventoryDto.add(inventoryForm);
        ProductPojo productPojo = productDao.select(productForm.getBarcode());
        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test
    public void testGetAll(){
//        List<InventoryPojo> inventoryPojoList = new ArrayList<InventoryPojo>();
//        for (int i = 1; i <= 3; i++){
//            InventoryPojo inventoryPojo = new InventoryPojo();
//            inventoryPojo.setId(i);
//            inventoryPojo.setQuantity(i);
//            inventoryDao.insert(inventoryPojo);
//            inventoryPojoList.add(inventoryPojo);
//        }
//        List<InventoryPojo> insertedInventoryPojos = inventoryDao.selectAll();
//        assertArrayEquals( inventoryPojoList.toArray(), insertedInventoryPojos.toArray());

    }

    @Test
    public void testGetReport() {

    }
}
