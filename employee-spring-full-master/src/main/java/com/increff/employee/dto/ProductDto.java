package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.ProductDtoHelper;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.forms.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;

@Service
public class ProductDto {

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	//TODO: restrict mrp to 2 decimal places
	public void add(ProductForm form) throws ApiException {
		ProductDtoHelper.emptyCheck(form);
		ProductDtoHelper.normalise(form);
		BrandPojo brandPojo = brandService.get(form.getBrand(), form.getCategory());
		if (brandPojo == null) {
			throw new ApiException("Error: Brand category combination does not exist");
		}
		ProductPojo existingProductPojo = productService.get(form.getBarcode());
		if (existingProductPojo != null) {
			throw new ApiException("Error: Barcode already exists");
		}
		ProductPojo productPojo = ProductDtoHelper.convert(form, brandPojo.getId());
		productService.add(productPojo);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo productPojo = productService.get(id);
		BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
		return ProductDtoHelper.convert(productPojo, brandPojo.getBrand(), brandPojo.getCategory());
	}

	public List<ProductData> getAll() throws ApiException {
		List<ProductPojo> productPojos = productService.getAll();
		List<ProductData> productDatas = new ArrayList<ProductData>();
		for (ProductPojo productPojo : productPojos) {
			BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
			productDatas.add(ProductDtoHelper.convert(productPojo, brandPojo.getBrand(), brandPojo.getCategory()));
		}
		return productDatas;
	}

	public void update(int id, ProductForm form) throws ApiException {
		ProductDtoHelper.emptyCheck(form);
		ProductDtoHelper.normalise(form);
		BrandPojo brandPojo = brandService.get(form.getBrand(), form.getCategory());
		if (brandPojo == null) {
			throw new ApiException("Error: Brand category combination does not exist");
		}
		ProductPojo productPojo = ProductDtoHelper.convert(form, brandPojo.getId());
		productService.update(id, productPojo);
	}

	public void delete(int id) throws ApiException {
		productService.delete(id);
	}

}
