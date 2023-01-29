package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.InventoryDtoHelper;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.InventoryReportData;
import com.increff.employee.model.forms.InventoryForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;

@Service
public class InventoryDto {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	public void add(InventoryForm form) throws ApiException {
		if (StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(String.valueOf(form.getQuantity()))) {
			throw new ApiException("Error: barcode/quantity can not be empty");
		}
		InventoryDtoHelper.normalise(form);
		//TODO: remove validate
		ProductPojo productPojo = validate(form.getBarcode());
		if (productPojo == null) {
			throw new ApiException("Error: product with given barcode does not exist");
		}
		InventoryPojo inventoryPojo = InventoryDtoHelper.convert(form, productPojo.getId());
		inventoryService.add(inventoryPojo);
	}

	public InventoryData get(int id) throws ApiException {
		ProductPojo productPojo = productService.get(id);
		InventoryPojo inventoryPojo = inventoryService.get(id);
		return InventoryDtoHelper.convert(inventoryPojo, productPojo.getBarcode());
	}

	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> list = inventoryService.getAll();
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			ProductPojo productPojo = productService.get(p.getId());
			list2.add(InventoryDtoHelper.convert(p, productPojo.getBarcode()));
		}
		return list2;
	}

	public List<InventoryReportData> getReportData() throws ApiException {
		List<InventoryData> inventoryDatas = getAll();
		List<InventoryReportData> reportDatas = new ArrayList<InventoryReportData>();

		HashMap<Integer, Integer> hash_map = new HashMap<Integer, Integer>();
		for (InventoryData inventoryData : inventoryDatas) {
			ProductPojo productPojo = productService.get(inventoryData.getBarcode());
			hash_map.put(productPojo.getBrand_category(),
					hash_map.getOrDefault(productPojo.getBrand_category(), 0) + inventoryData.getQuantity());
		}
		for (int key : hash_map.keySet()) {
			InventoryReportData inventoryReportData = new InventoryReportData();
			BrandPojo brandPojo = brandService.get(key);
			inventoryReportData.setBrand(brandPojo.getBrand());
			inventoryReportData.setCategory(brandPojo.getCategory());
			inventoryReportData.setQuantity(hash_map.get(key));
			reportDatas.add(inventoryReportData);
		}
		return reportDatas;
	}

	public void update(int id, InventoryForm form) throws ApiException {
		if (StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(String.valueOf(form.getQuantity()))) {
			throw new ApiException("Error: barcode/quantity can not be empty");
		}
		InventoryDtoHelper.normalise(form);
		ProductPojo productPojo = validate(form.getBarcode());
		if (productPojo == null) {
			throw new ApiException("Error: product with given barcode does not exist");
		}
		InventoryPojo b = InventoryDtoHelper.convert(form, id);
		inventoryService.update(id, b);
	}

	public void delete(int id) throws ApiException {
		inventoryService.delete(id);
	}

	public ProductPojo validate(String barcode) {
		ProductPojo productPojo = productService.get(barcode);
		return productPojo;
	}
}
