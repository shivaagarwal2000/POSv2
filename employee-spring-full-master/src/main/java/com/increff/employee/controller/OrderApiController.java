package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.data.CommonOrderItemData;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.forms.OrderItemForm;
import com.increff.employee.model.forms.SalesReportForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;



    @ApiOperation(value = "Adds an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void addOrder(@RequestBody List<OrderItemForm> forms) throws ApiException {
        orderDto.addOrder(forms);
    }

    @ApiOperation(value = "Gets all order items")
    @RequestMapping(path = "/api/order/all", method = RequestMethod.GET)
    public List<CommonOrderItemData> getAllOrderItems() throws ApiException {
        return orderDto.getAllOrderItems();
    }

    @ApiOperation(value = "Edit an order item")
    @RequestMapping(path = "/api/order/editItem/{id}", method = RequestMethod.PUT)
    public void editOrderItem(@PathVariable int id, @RequestBody OrderItemForm orderItemForm) throws ApiException {
        orderDto.editOrderItem(id, orderItemForm);
    }

    @ApiOperation(value = "Place order")
    @RequestMapping(path = "/api/order/place/{id}", method = RequestMethod.PUT)
    public void placeOrder(@PathVariable int id) throws ApiException {
        orderDto.placeOrder(id);
    }

    @ApiOperation(value = "Gets all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() throws ApiException {
        return orderDto.getAll();
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

//	@ApiOperation(value = "get order report")
//	@RequestMapping(path = "/api/order/report", method = RequestMethod.POST)
//	public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
//		salesReportForm.setStartDate(salesReportForm.getStartDate().replace('-', '/'));
//		salesReportForm.setEndDate(salesReportForm.getEndDate().replace('-', '/'));
//		List<SalesReportData> salesReportDatas = orderDto.getSalesReportDatas(salesReportForm);
//		return salesReportDatas;
//	}

    //TODO: make method as GET
    //TODO: only "placed" orders to be fetched
    @ApiOperation(value = "get order report")
    @RequestMapping(path = "/api/order/report", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
//        salesReportForm.setStartDate(salesReportForm.getStartDate().replace('-', '/'));
//        salesReportForm.setEndDate(salesReportForm.getEndDate().replace('-', '/'));
        List<SalesReportData> salesReportDatas = orderDto.getSalesReportDatas(salesReportForm);
        return salesReportDatas;
    }

    @ApiOperation(value = "get order invoice")
    @RequestMapping(path = "/api/order/invoice/{id}", method = RequestMethod.GET)
    public String getInvoice(@PathVariable int id) throws ApiException, IOException {
        return orderDto.getInvoice(id);
    }

}
