package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class InventoryDaoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;
    @Test
    public void testInsert(){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        BrandPojo brandPojo1 = brandDao.select(1);
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }

    @Test
    public void testDelete(){

    }

    @Test
    public void testSelectById(){

    }

    @Test
    public void testSelectAll(){

    }

    @Test
    public void testUpdate(){

    }

    @Test
    public void testSelectByBrandCategory(){

    }
}
