package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BrandDaoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;

    @Test
    public void testInsert() {
        //test adding brandpojo through dao layer
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandDao.insert(brandPojo);
        BrandPojo brandPojo1 = brandDao.select(1);
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }

    @Test
    public void testDelete() {
        //test deleting brandpojo through dao layer
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandDao.insert(brandPojo);
        brandDao.delete(1);
        BrandPojo brandPojo1 = brandDao.select(1);
        if (Objects.isNull(brandPojo1) == false) {
            fail("Brand Dao unable to delete entry");
        }
    }

    @Test
    public void testSelectById() {
        //test retrieval of brandpojo using id
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandDao.insert(brandPojo);
        BrandPojo brandPojo1 = brandDao.select(1);
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }

    @Test
    public void testSelectAll() {
        //test retrieval of all brand pojo through dao layer

    }

    @Test
    public void testSelectByBrandCategory() {
        //test retrieval of brandpojo using brand and category
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandDao.insert(brandPojo);
        BrandPojo brandPojo1 = brandDao.select(brandPojo.getBrand(), brandPojo.getCategory());
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }
}
