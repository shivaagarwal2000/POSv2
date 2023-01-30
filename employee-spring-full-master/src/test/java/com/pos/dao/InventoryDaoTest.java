package com.pos.dao;

import com.pos.pojo.InventoryPojo;
import com.pos.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventoryDaoTest extends AbstractUnitTest {

    @Autowired
    private InventoryDao inventoryDao;

    @Test
    public void testInsert() {
        //test insertion of inventorypojo using dao layer
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(1);
        inventoryDao.insert(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryDao.select(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
    }

    @Test
    public void testDelete() {
        //test deletion of inventorypojo using dao layer
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(1);
        inventoryDao.insert(inventoryPojo);
        inventoryDao.delete(1);
        InventoryPojo inventoryPojo1 = inventoryDao.select(1);
        if (Objects.isNull(inventoryPojo1) == false) {
            fail("inventory dao delete unable to delete entry");
        }

    }

    @Test
    public void testSelectById() {
        //test retrieval of inventorypojo using dao layer
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(1);
        inventoryDao.insert(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryDao.select(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
    }

    @Test
    public void testSelectAll() {
        //test retrieval of all inventory pojo
        List<InventoryPojo> inventoryPojoList = new ArrayList<InventoryPojo>();
        for (int i = 1; i <= 3; i++){
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(i);
            inventoryPojo.setQuantity(i);
            inventoryDao.insert(inventoryPojo);
            inventoryPojoList.add(inventoryPojo);
        }
        List<InventoryPojo> insertedInventoryPojos = inventoryDao.selectAll();
        assertArrayEquals( inventoryPojoList.toArray(), insertedInventoryPojos.toArray());
    }

}
