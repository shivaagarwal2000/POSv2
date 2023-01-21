package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.data.SalesReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.forms.OrderItemForm;
import com.increff.employee.model.forms.SalesReportForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

	@Autowired
	private OrderDto orderDto;

	@ApiOperation(value = "Gets all order")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderData> getAllOrders() throws ApiException {
		return orderDto.getAll();
	}

	@ApiOperation(value = "places an order")
	@RequestMapping(path = "/api/placeOrder", method = RequestMethod.POST)
	public void placeOrder(@RequestBody List<OrderItemForm> forms) throws ApiException {

		orderDto.add(forms);
	}

	@ApiOperation(value = "Gets all order items")
	@RequestMapping(path = "/api/placeOrder", method = RequestMethod.GET)
	public List<OrderItemData> getAllOrderItems() throws ApiException {
		return orderDto.getAllOrderItems();
	}

	@ApiOperation(value = "gets items for order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
	public List<OrderItemData> get(@PathVariable int id) throws ApiException {
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

	@ApiOperation(value = "get order report")
	@RequestMapping(path = "/api/order/report", method = RequestMethod.POST)
	public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
		salesReportForm.setStartDate(salesReportForm.getStartDate().replace('-', '/'));
		salesReportForm.setEndDate(salesReportForm.getEndDate().replace('-', '/'));
		List<SalesReportData> salesReportDatas = orderDto.getSalesReportDatas(salesReportForm);
		return salesReportDatas;
	}

}
