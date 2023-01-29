package com.increff.employee.service;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InventoryServiceTest extends AbstractUnitTest {

    @Autowired
    private InventoryService inventoryService;

    @Test // TODO: refactor: add comments
    public void testAdd() throws ApiException {
        //test adding brandPojo
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(10);
        inventoryService.add(inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.get(1);
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
    }

    @Test
    public void testAddNegativeQuantity(){
        try{
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(1);
            inventoryPojo.setQuantity(-10);
            inventoryService.add(inventoryPojo);
            InventoryPojo inventoryPojo1 = inventoryService.get(1);
            fail("inventory service inserting negative quantity");
        }
        catch (ApiException apiException){
            final String ERROR_MSG = "Error: quantity can not be negative";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

    @Test
    public void testGetId() {
        //test retrieval of brandPojo
        try {
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(1);
            inventoryPojo.setQuantity(10);
            inventoryService.add(inventoryPojo);
            InventoryPojo inventoryPojo1 = inventoryService.get(1);
            assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
            assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
        } catch (ApiException apiException) {
            fail("Get by Id method not able to retrieve correct entry");
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = new ArrayList<InventoryPojo>();
        for (int i = 1; i <= 3; i++){
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(i);
            inventoryPojo.setQuantity(i);
            inventoryService.add(inventoryPojo);
            inventoryPojoList.add(inventoryPojo);
        }
        List<InventoryPojo> insertedInventoryPojos = inventoryService.getAll();
        assertArrayEquals( inventoryPojoList.toArray(), insertedInventoryPojos.toArray());
    }

    @Test
    public void testUpdate() throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(1);
        inventoryPojo.setQuantity(10);
        inventoryService.add(inventoryPojo);
        inventoryPojo.setQuantity(20);
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        InventoryPojo inventoryPojo1 = inventoryService.get(inventoryPojo.getId());
        assertEquals(inventoryPojo.getId(), inventoryPojo1.getId());
        assertEquals(inventoryPojo.getQuantity(), inventoryPojo1.getQuantity());
    }

    @Test
    public void testUpdateNegativeQuantity() {
        try {
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(1);
            inventoryPojo.setQuantity(10);
            inventoryService.add(inventoryPojo);
            inventoryPojo.setQuantity(-20);
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
            fail("inventory service updating to negative quantity");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: quantity can not be negative";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

    @Test
    public void testDelete() {
        try {
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(1);
            inventoryPojo.setQuantity(10);
            inventoryService.add(inventoryPojo);
            inventoryService.delete(1);
            InventoryPojo inventoryPojo1 = inventoryService.get(1);
            fail("inventory service not deleting the entry");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Inventory with given ID does not exit, id: 1";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

    @Test
    public void testValidateQuantity(){
        try {

            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(1);
            inventoryPojo.setQuantity(10);
            inventoryService.validateQuantity(inventoryPojo);
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: quantity can not be negative";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

}
