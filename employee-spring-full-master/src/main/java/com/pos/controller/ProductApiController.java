package com.pos.controller;

import java.util.List;

import com.pos.dto.ProductDto;
import com.pos.model.data.ProductData;
import com.pos.model.forms.ProductForm;
import com.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductApiController {

	@Autowired
	private ProductDto productDto;

	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {
		productDto.add(form);
	}

	@ApiOperation(value = "Adds bulk products")
	@RequestMapping(path = "/api/product/all", method = RequestMethod.POST)
	public void bulkAdd(@RequestBody List<ProductForm> forms) throws ApiException {
		productDto.bulkAdd(forms);
	}

	@ApiOperation(value = "gets a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		return productDto.get(id);
	}

	@ApiOperation(value = "Gets list of all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll() throws ApiException {
		return productDto.getAll();
	}

	@ApiOperation(value = "updates a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
		productDto.update(id, form);
	}

	@ApiOperation(value = "delete a product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		productDto.delete(id);
	}

}
