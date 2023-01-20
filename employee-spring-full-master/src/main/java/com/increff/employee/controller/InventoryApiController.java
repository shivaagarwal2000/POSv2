package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.InventoryReportData;
import com.increff.employee.model.forms.InventoryForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {

	@Autowired
	private InventoryDto inventoryDto;

	@ApiOperation(value = "Adds a Inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public void add(@RequestBody InventoryForm form) throws ApiException {
		inventoryDto.add(form);
	}

	@ApiOperation(value = "gets a inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		return inventoryDto.get(id);
	}

	@ApiOperation(value = "Gets list of all inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll() throws ApiException {
		return inventoryDto.getAll();
	}

	@ApiOperation(value = "updates a inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException {
		inventoryDto.update(id, form);
	}

	@ApiOperation(value = "delete a inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		inventoryDto.delete(id);
	}

	@ApiOperation(value = "get report")
	@RequestMapping(path = "/api/inventory/report", method = RequestMethod.GET)
	public List<InventoryReportData> getReport() throws ApiException {
		System.out.println("he");
		return inventoryDto.getReportData();
	}

}
