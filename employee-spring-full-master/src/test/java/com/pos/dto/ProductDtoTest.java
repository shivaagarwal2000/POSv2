package com.pos.dto;

import com.pos.helper.posTestHelper;
import com.pos.model.data.ProductData;
import com.pos.model.forms.BrandForm;
import com.pos.model.forms.ProductForm;
import com.pos.service.AbstractUnitTest;
import org.commons.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = posTestHelper.createProductForm("  Brand ", "category  ", "name", "barcode", 1.00);
        productDto.add(productForm);
        ProductData productData = productDto.get(1);
        assertEquals("brand", productData.getBrand());
        assertEquals("category", productData.getCategory());
        assertEquals(productForm.getBarcode(), productData.getBarcode());
        assertEquals(productForm.getName(), productData.getName());
        assertEquals(productForm.getMrp(), productData.getMrp(), 0.01);
    }

    @Test(expected = ApiException.class)
    public void testEmptyBrand() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm("", brandForm.getCategory(), "name", "barcode", 1.00);
            productDto.add(productForm);
            fail("product dto add: empty brand field getting accepted");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: brand can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testEmptyCategory() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), "", "name", "barcode", 1.00);
            productDto.add(productForm);
            fail("product dto add: empty category field getting accepted");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: category can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testEmptyBarcode() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "", 1.00);
            productDto.add(productForm);
            fail("product dto add: empty barcode field getting accepted");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testEmptyMrp() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", null);
            productDto.add(productForm);
            fail("product dto add: empty mrp field getting accepted");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: mrp can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testEmptyName() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "", "barcode", 1.00);
            productDto.add(productForm);
            fail("product dto add: empty name field getting accepted");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: name can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutBrand() throws ApiException {
        try {
            ProductForm productForm = posTestHelper.createProductForm("brand", "category", "name", "barcode", 1.00);
            productDto.add(productForm);
            fail("Product dto adding the product without required brand entity existence");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Brand category combination does not exist";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithNegativeMrp() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", -5.00);
            productDto.add(productForm);
            fail("Product dto adding the product with negative mrp");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: mrp has to be positive";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testDuplicateProduct() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            productDto.add(productForm);
            fail("Product dto adds duplicate product");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode already exists";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testGetById() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
        productDto.add(productForm);
        ProductData productData = productDto.get(1);
        assertEquals(productForm.getBrand(), productData.getBrand());
        assertEquals(productForm.getCategory(), productData.getCategory());
        assertEquals(productForm.getBarcode(), productData.getBarcode());
        assertEquals(productForm.getName(), productData.getName());
        assertEquals(productForm.getMrp(), productData.getMrp(), 0.01);
    }

    @Test
    public void testGetAll() throws ApiException {
        List<ProductData> productDataList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BrandForm brandForm = posTestHelper.createBrandForm("brand" + i, "category" + i);
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name" + i, "barcode" + i, (double) i);
            productDto.add(productForm);
            ProductData productData = posTestHelper.createProductData(brandForm.getBrand(), brandForm.getCategory(), "name" + i, "barcode" + i, (double) i, i);
            productDataList.add(productData);
        }
        List<ProductData> insertedProductData = productDto.getAll();
        for (int i = 1; i <= 3; i++) {
            assertEquals(productDataList.get(i - 1).getId(), productDataList.get(i - 1).getId());
            assertEquals(productDataList.get(i - 1).getMrp(), productDataList.get(i - 1).getMrp());
            assertEquals(productDataList.get(i - 1).getCategory(), productDataList.get(i - 1).getCategory());
            assertEquals(productDataList.get(i - 1).getBrand(), productDataList.get(i - 1).getBrand());
            assertEquals(productDataList.get(i - 1).getName(), productDataList.get(i - 1).getName());
            assertEquals(productDataList.get(i - 1).getBarcode(), productDataList.get(i - 1).getBarcode());
        }
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
        productDto.add(productForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "newname", "newbarcode", 2.00);
        productDto.update(1, productForm1);
        ProductData productData = productDto.get(1);
        assertEquals(productForm1.getName(), productData.getName());
        assertEquals(productForm1.getMrp(), productData.getMrp(), 0.01);
        assertEquals(productForm1.getBrand(), productData.getBrand());
        assertEquals(productForm1.getCategory(), productData.getCategory());
        assertEquals(productForm.getBarcode(), productData.getBarcode());
    }

    @Test(expected = ApiException.class)
    public void testDelete() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            productDto.delete(1);
            ProductData productData = productDto.get(1);
            fail("product dto delete method not able to delete correct entry");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product with given ID does not exist, id: 1";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }
}
