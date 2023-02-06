package com.pos.service;

import com.pos.helper.posTestHelper;
import com.pos.pojo.InventoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InventoryServiceTest extends AbstractUnitTest {

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testAdd() throws ApiException {
        //test adding inventorypojo using service layer
        InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
        inventoryService.add(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.get(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
    }

    @Test(expected = ApiException.class)
    public void testAddNegativeQuantity() throws ApiException {
        //test adding inventorypojo with negative quantity
        try {
            InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, -10);
            inventoryService.add(inventoryPojo);
            //fail("inventory service inserting negative quantity");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: quantity can not be negative";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testGetId() throws ApiException {
        //test retrieval of inventorypojo using id
        InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
        inventoryService.add(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.get(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
    }

    @Test
    public void testGetCheckId() throws ApiException {
        //test retrieval of inventorypojo using id
        InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
        inventoryService.add(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.getCheck(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
    }

    @Test(expected = ApiException.class)
    public void testGetCheckWrongId() throws ApiException {
        //test retrieval of inventorypojo using id
        try {
            InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
            inventoryService.add(inventoryPojo);
            InventoryPojo inventoryPojo1 = inventoryService.getCheck(3);
            fail();
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Inventory with given ID does not exit, id: 3";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        //test retrieval of all inventorypojo
        List<InventoryPojo> inventoryPojoList = new ArrayList<InventoryPojo>();
        for (int i = 1; i <= 3; i++) {
            InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(i, i);
            inventoryService.add(inventoryPojo);
            inventoryPojoList.add(inventoryPojo);
        }
        List<InventoryPojo> insertedInventoryPojos = inventoryService.getAll();
        assertArrayEquals(inventoryPojoList.toArray(), insertedInventoryPojos.toArray());
    }

    @Test
    public void testUpdate() throws ApiException {
        //test update of inventorypojo using service layer
        InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
        inventoryService.add(inventoryPojo);
        inventoryPojo.setQuantity(20);
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.get(inventoryPojo.getId());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
    }

    @Test(expected = ApiException.class)
    public void testUpdateNegativeQuantity() throws ApiException {
        try {
            InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
            inventoryService.add(inventoryPojo);
            inventoryPojo.setQuantity(-20);
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
            fail("inventory service updating to negative quantity");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: quantity can not be negative";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testDelete() throws ApiException {
        InventoryPojo inventoryPojo = posTestHelper.createInventoryPojo(1, 10);
        inventoryService.add(inventoryPojo);
        inventoryService.delete(1);
        InventoryPojo inventoryPojo1 = inventoryService.get(1);
        if (!Objects.isNull(inventoryPojo1)) {
            fail("inventory service not deleting the entry");
        }
    }
}
