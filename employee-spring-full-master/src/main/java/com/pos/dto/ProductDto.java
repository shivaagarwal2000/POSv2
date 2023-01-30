package com.pos.dto;

import com.pos.helper.ProductDtoHelper;
import com.pos.model.data.ProductData;
import com.pos.model.forms.ProductForm;
import com.pos.pojo.BrandPojo;
import com.pos.pojo.ProductPojo;
import com.pos.service.ApiException;
import com.pos.service.BrandService;
import com.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    //TODO: restrict mrp to 2 decimal places
    public void add(ProductForm form) throws ApiException {
		BrandPojo brandPojo = validate(form);
        ProductPojo existingProductPojo = productService.get(form.getBarcode());// TODO Use getCheck Priority: 5
        if (existingProductPojo != null) {
            throw new ApiException("Error: Barcode already exists");
        }
        ProductPojo productPojo = ProductDtoHelper.convert(form, brandPojo.getId());
        productService.add(productPojo);
    }

    public void bulkAdd(List<ProductForm> forms) throws ApiException {
		for (ProductForm productForm: forms) {
			validate(productForm);
		}
		for (ProductForm productForm: forms) {
            ProductPojo existingProductPojo = productService.get(productForm.getBarcode());
            if (Objects.isNull(existingProductPojo) == false) {
                BrandPojo brandPojo = validate(productForm);
                productService.update(productForm.getBarcode(), ProductDtoHelper.convert(productForm, brandPojo.getId()));
            }
            else {
                add(productForm);
            }
		}
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
        BrandPojo brandPojo = validate(form);
        ProductPojo productPojo = ProductDtoHelper.convert(form, brandPojo.getId());
        productService.update(id, productPojo);
    }

    public void delete(int id) throws ApiException {
        productService.delete(id);
    }

    public BrandPojo validate(ProductForm form) throws ApiException {
        ProductDtoHelper.emptyCheck(form);
        ProductDtoHelper.normalise(form);
        BrandPojo brandPojo = brandService.get(form.getBrand(), form.getCategory());
        if (brandPojo == null) {
            throw new ApiException("Error: Brand category combination does not exist");
        }
        if (form.getMrp() <= 0) {
            throw new ApiException("Error: mrp can not be negative");
        }
		return brandPojo;
    }

}
