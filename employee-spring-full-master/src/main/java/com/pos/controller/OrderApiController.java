package com.pos.controller;

import com.pos.dto.OrderDto;
//import com.pos.model.data.CommonOrderItemData;
import org.commons.CommonOrderItemData;
import com.pos.model.data.OrderData;
import com.pos.model.data.SalesReportData;
import com.pos.model.forms.OrderItemForm;
import com.pos.model.forms.SalesReportForm;
import org.commons.util.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Adds an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public int addOrder(@RequestBody List<OrderItemForm> forms) throws ApiException {
        return orderDto.addOrder(forms);
    }

    @ApiOperation(value = "Edit an order item")
    @RequestMapping(path = "/api/order/editItem/{id}", method = RequestMethod.PUT)
    public void editOrderItem(@PathVariable int id, @RequestBody OrderItemForm orderItemForm) throws ApiException {
        orderDto.editOrderItem(id, orderItemForm);
    }

    @ApiOperation(value = "delete an order item")
    @RequestMapping(path = "/api/order/deleteItem/{id}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable int id) throws ApiException {
        orderDto.deleteOrderItem(id);
    }

    @ApiOperation(value = "Place order")
    @RequestMapping(path = "/api/order/place/{id}", method = RequestMethod.PUT)
    public void placeOrder(@PathVariable int id) throws ApiException, IOException {
        orderDto.placeOrder(id);
    }

    @ApiOperation(value = "Gets all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Gets item detail")
    @RequestMapping(path = "/api/order/item/{id}", method = RequestMethod.GET)
    public CommonOrderItemData getOrderItemData(@PathVariable int id) throws ApiException {
        //get detail for an order item given id -- helps while editing
        return orderDto.getOrderItem(id);
    }

    @ApiOperation(value = "gets items for order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public List<CommonOrderItemData> get(@PathVariable int id) throws ApiException {
        return orderDto.getItemDatas(id);
    }

    @ApiOperation(value = "delete a order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderDto.deleteOrder(id);
    }

    @ApiOperation(value = "get order report")
    @RequestMapping(path = "/api/order/report", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
        List<SalesReportData> salesReportDatas = orderDto.getSalesReportDatas(salesReportForm);
        return salesReportDatas;
    }

    @ApiOperation(value = "get order invoice")
    @RequestMapping(path = "/api/order/invoice/{id}", method = RequestMethod.GET)
    public String getInvoice(@PathVariable int id) throws ApiException, IOException {
        return orderDto.getInvoice(id);
    }

}
