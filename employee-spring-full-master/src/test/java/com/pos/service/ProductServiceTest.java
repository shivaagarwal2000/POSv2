package com.pos.service;

import com.pos.helper.posTestHelper;
import com.pos.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTest extends AbstractUnitTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAdd() throws ApiException {
        //test adding productPojo through service layer
        ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
        productService.add(productPojo);
        ProductPojo productPojo1 = productService.get(1);
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
    }

    @Test
    public void testGetById() throws ApiException {
        //test retrieval of productPojo
        ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
        productService.add(productPojo);
        ProductPojo productPojo1 = productService.get(1);
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
    }

    @Test(expected = ApiException.class)
    public void testGetByWrongId() throws ApiException {
        //test retrieval of productPojo
        try {
            ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
            productService.add(productPojo);
            ProductPojo productPojo1 = productService.get(3);
            fail("Error: get not functioning correctly");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product with given ID does not exist, id: 3";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        List<ProductPojo> productPojoList = new ArrayList<ProductPojo>();
        for (int i = 1; i <= 3; i++) {
            ProductPojo productPojo = posTestHelper.createProductPojo("barcode" + i, 5, 1, "name" + i);
            productService.add(productPojo);
            productPojoList.add(productPojo);
        }
        List<ProductPojo> insertedProductPojos = productService.getAll();
        assertArrayEquals(productPojoList.toArray(), insertedProductPojos.toArray());
    }

    @Test
    public void testUpdate() throws ApiException {
        ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
        productService.add(productPojo);
        ProductPojo productPojo1 = posTestHelper.createProductPojo("newbarcode", 5, 1, "newname");
        productService.update(1, productPojo1);
        ProductPojo updatedProductPojo = productService.get(1);
        assertEquals(productPojo.getBarcode(), updatedProductPojo.getBarcode());
        assertEquals(productPojo1.getMrp(), updatedProductPojo.getMrp(), 0.01);
        assertEquals(productPojo1.getName(), updatedProductPojo.getName());
        assertEquals(productPojo1.getBrand_category(), updatedProductPojo.getBrand_category());
    }

    @Test
    public void testDelete() throws ApiException {
        try {
            ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
            productService.add(productPojo);
            productService.delete(1);
            ProductPojo productPojo1 = productService.get(1);
            fail("product service delete unable to delete entry");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product with given ID does not exist, id: 1";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

    @Test
    public void testGetByBarcode() throws ApiException {
        //test retrieval of brandPojo
        ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
        productService.add(productPojo);
        ProductPojo productPojo1 = productService.get(productPojo.getBarcode());
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
    }

    @Test
    public void testGetCheckByBarcode() throws ApiException {
        ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
        productService.add(productPojo);
        ProductPojo productPojo1 = productService.getCheck(productPojo.getBarcode());
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
    }

    @Test(expected = ApiException.class)
    public void testGetCheckByWrongBarcode() throws ApiException {
        try {
            ProductPojo productPojo = posTestHelper.createProductPojo("barcode", 5, 1, "name");
            productService.add(productPojo);
            ProductPojo productPojo1 = productService.getCheck("wrongBarcode");
            fail("product service getcheckbarcode not working");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product does not exists for barcode: wrongBarcode";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }
}
