package com.pos.helper;

import com.pos.model.data.ProductData;
import com.pos.model.forms.ProductForm;
import com.pos.pojo.ProductPojo;
import com.pos.service.ApiException;
import com.pos.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductDtoHelper {

	public static ProductData convert(ProductPojo productPojo, String brand, String category) throws ApiException {
		ProductData productData = new ProductData();
		productData.setBarcode(productPojo.getBarcode());
		productData.setBrand(brand);
		productData.setCategory(category);
		productData.setId(productPojo.getId());
		productData.setMrp(productPojo.getMrp());
		productData.setName(productPojo.getName());
		return productData;
	}

	public static ProductPojo convert(ProductForm form, int brandCategoryId) throws ApiException {
		ProductPojo productPojo = new ProductPojo();
		productPojo.setBarcode(form.getBarcode());
		productPojo.setBrand_category(brandCategoryId);
		productPojo.setMrp(form.getMrp());
		productPojo.setName(form.getName());
		return productPojo;
	}

	public static void emptyCheck(ProductForm form) throws ApiException {
		if (StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(form.getBrand())
				|| StringUtil.isEmpty(form.getCategory()) || StringUtil.isEmpty(String.valueOf(form.getMrp()))
				|| StringUtil.isEmpty(form.getName())) {
			if (StringUtil.isEmpty(form.getBarcode())) {
				throw new ApiException("Error: barcode can not be empty");
			} else if (StringUtil.isEmpty(form.getBrand())) {
				throw new ApiException("Error: brand can not be empty");
			} else if (StringUtil.isEmpty(form.getCategory())) {
				throw new ApiException("Error: category can not be empty");
			} else if (StringUtil.isEmpty(String.valueOf(form.getMrp()))) {
				throw new ApiException("Error: mrp can not be empty");
			} else {
				throw new ApiException("Error: name can not be empty");
			}

		}
	}

	public static Double round(Double d, int precise) {
		BigDecimal bigDecimal = new BigDecimal(d);
		bigDecimal = bigDecimal.setScale(precise, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

	public static void normalise(ProductForm form) {
		form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
		form.setBrand(StringUtil.toLowerCase(form.getBrand()));
		form.setCategory(StringUtil.toLowerCase(form.getCategory()));
		form.setName(StringUtil.toLowerCase(form.getName()));
		form.setMrp(round(form.getMrp(), 2));
	}

}
