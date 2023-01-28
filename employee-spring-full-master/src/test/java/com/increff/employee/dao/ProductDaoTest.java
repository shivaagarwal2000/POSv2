package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductDaoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsert() {
        //test inserting productpojo through dao layer
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("barcode");
        productPojo.setMrp(4);
        productPojo.setBrand_category(1);
        productPojo.setName("name");
        productDao.insert(productPojo);
        ProductPojo productPojo1 = productDao.select(1);
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
    }

    @Test
    public void testDelete() {
        //test deleting productpojo through dao layer
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("barcode");
        productPojo.setMrp(4);
        productPojo.setBrand_category(1);
        productPojo.setName("name");
        productDao.insert(productPojo);
        productDao.delete(1);
        ProductPojo productPojo1 = productDao.select(1);
        if (Objects.isNull(productPojo1) == false) {
            fail("Product Dao: delete unable to delete entry");
        }
    }

    @Test
    public void testSelectById() {
        //test retrieval of productpojo using id through dao layer
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("barcode");
        productPojo.setMrp(4);
        productPojo.setBrand_category(1);
        productPojo.setName("name");
        productDao.insert(productPojo);
        ProductPojo productPojo1 = productDao.select(1);
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
    }

    @Test
    public void testSelectByBarcode() {
        //test retrieval of productpojo using barcode through dao layer
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode("barcode");
        productPojo.setMrp(4);
        productPojo.setBrand_category(1);
        productPojo.setName("name");
        productDao.insert(productPojo);
        ProductPojo productPojo1 = productDao.select(productPojo.getBarcode());
        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
        assertEquals(productPojo.getName(), productPojo1.getName());
        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
    }

    @Test
    public void testSelectAll() {

    }

}
