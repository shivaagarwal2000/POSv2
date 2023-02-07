package com.pos.service;

import com.pos.pojo.OrderPojo;
import org.commons.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static com.pos.pojo.TableConstants.PLACED_STATUS;
import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceTest extends AbstractUnitTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAdd() throws ApiException {
        //test adding brandPojo
        OrderPojo orderPojo = orderService.add();
        OrderPojo insertedOrderPojo = orderService.get(1);
        assertEquals(orderPojo.getId(), insertedOrderPojo.getId());
        assertEquals(orderPojo.getStatus(), insertedOrderPojo.getStatus());
        assertEquals(orderPojo.getTime(), insertedOrderPojo.getTime());
    }

    @Test
    public void testGetById() throws ApiException {
        OrderPojo orderPojo = orderService.add();
        OrderPojo insertedOrderPojo = orderService.get(1);
        assertEquals(orderPojo.getId(), insertedOrderPojo.getId());
        assertEquals(orderPojo.getStatus(), insertedOrderPojo.getStatus());
        assertEquals(orderPojo.getTime(), insertedOrderPojo.getTime());
    }

    @Test(expected = ApiException.class)
    public void testGetByWrongId() throws ApiException {
        try {
            OrderPojo orderPojo = orderService.add();
            OrderPojo insertedOrderPojo = orderService.get(3);
            fail("order service get not detecting error");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Order does not exist";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testPlaceOrder() throws ApiException {
        OrderPojo orderPojo = orderService.add();
        orderService.placeOrder(1);
        OrderPojo placedOrderPojo = orderService.get(1);
        assertEquals(orderPojo.getId(), placedOrderPojo.getId());
        assertEquals(PLACED_STATUS, placedOrderPojo.getStatus());
    }


    @Test
    public void testGetAll() throws ApiException {
        List<OrderPojo> orderPojoList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            OrderPojo orderPojo = orderService.add();
            orderPojoList.add(orderPojo);
        }
        List<OrderPojo> insertedOrderPojoList = orderService.getAll();
        assertArrayEquals(orderPojoList.toArray(), insertedOrderPojoList.toArray());
    }

    @Test
    public void testDelete() throws ApiException {
        OrderPojo orderPojo = orderService.add();
        orderService.delete(1);
        try {
            OrderPojo insertedOrderPojo = orderService.get(1);
            fail("order service unable to delete entry");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Order does not exist";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

}
