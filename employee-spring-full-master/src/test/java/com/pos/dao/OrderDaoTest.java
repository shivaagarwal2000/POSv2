package com.pos.dao;

import com.pos.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderDaoTest extends AbstractUnitTest {

    @Autowired
    private OrderDao orderDao;
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
