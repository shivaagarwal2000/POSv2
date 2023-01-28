package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventoryDaoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;
    @Test
    public void testInsert(){
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
