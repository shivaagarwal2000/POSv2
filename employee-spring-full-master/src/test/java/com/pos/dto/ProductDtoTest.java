package com.pos.dto;

import com.pos.dao.BrandDao;
import com.pos.dao.ProductDao;
import com.pos.helper.DtoTestHelper;
import com.pos.model.data.ProductData;
import com.pos.model.forms.BrandForm;
import com.pos.model.forms.ProductForm;
import com.pos.pojo.BrandPojo;
import com.pos.service.AbstractUnitTest;
import com.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException { // TODO: include to have trim and lowercase effect
        BrandForm brandForm = DtoTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = DtoTestHelper.createProductForm("  Brand ", "category  ", "name", "barcode", 1.00);
        productDto.add(productForm);
        ProductData productData = productDto.get(1);
        assertEquals("brand", productData.getBrand());
        assertEquals(productForm.getCategory(), productData.getCategory());
        assertEquals(productForm.getBarcode(), productData.getBarcode());
        assertEquals(productForm.getName(), productData.getName());
        assertEquals(productForm.getMrp(), productData.getMrp(), 0.01);
    }
//
//    @Test
//    public void testEmptyBrand() {
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand");
//            brandPojo.setCategory("category");
//            brandDao.insert(brandPojo);
//            ProductForm productForm = new ProductForm();
//            productForm.setCategory(brandPojo.getCategory());
//            productForm.setBarcode("barcode");
//            productForm.setMrp(1);
//            productForm.setName("name");
//            productDto.add(productForm);
//            fail("product dto add: empty brand field getting accepted");
//        } catch (ApiException apiException) {
//            final String ERROR_MSG = "Error: brand can not be empty";
//            assertEquals(ERROR_MSG, apiException.getMessage());
//        }
//    }
////
////    @Test
////    public void testEmptyCategory() {
////        try {
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand(brandPojo.getBrand());
////            productForm.setBarcode("barcode");
////            productForm.setMrp(1);
////            productForm.setName("name");
////            productDto.add(productForm);
////            fail("product dto add: empty category field getting accepted");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: category can not be empty";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testEmptyBarcode() {
////        try {
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand(brandPojo.getBrand());
////            productForm.setCategory(brandPojo.getCategory());
////            productForm.setMrp(1);
////            productForm.setName("name");
////            productDto.add(productForm);
////            fail("product dto add: empty barcode field getting accepted");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: barcode can not be empty";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testEmptyMrp() { // TODO: refactor - mini: mrp empty not working
//////        try {
//////            BrandPojo brandPojo = new BrandPojo();
//////            brandPojo.setBrand("brand");
//////            brandPojo.setCategory("category");
//////            brandDao.insert(brandPojo);
//////            ProductForm productForm = new ProductForm();
//////            productForm.setBrand(brandPojo.getBrand());
//////            productForm.setCategory(brandPojo.getCategory());
//////            productForm.setBarcode("barcode");
//////            productForm.setName("name");
//////            productDto.add(productForm);
//////            fail("product dto add: empty mrp field getting accepted");
//////        } catch (ApiException apiException) {
//////            final String ERROR_MSG = "Error: mrp can not be empty";
//////            assertEquals(ERROR_MSG, apiException.getMessage());
//////        }
////    }
////
////    @Test
////    public void testEmptyName() {
////        try {
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand(brandPojo.getBrand());
////            productForm.setCategory(brandPojo.getCategory());
////            productForm.setBarcode("barcode");
////            productForm.setMrp(1);
////            productDto.add(productForm);
////            fail("product dto add: empty name field getting accepted");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: name can not be empty";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testAddWithoutBrand() {
////        try {
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand("brand");
////            productForm.setCategory("category");
////            productForm.setBarcode("barcode");
////            productForm.setMrp(1);
////            productForm.setName("name");
////            productDto.add(productForm);
////            fail("Product dto adding the product without required brand entity existence");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: Brand category combination does not exist";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testAddWithNegativeMrp() {
////        try {
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand("brand");
////            productForm.setCategory("category");
////            productForm.setBarcode("barcode");
////            productForm.setMrp(-5);
////            productForm.setName("name");
////            productDto.add(productForm);
////            fail("Product dto adding the product with negative mrp");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: mrp can not be negative";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testDuplicateProduct() {
////        try {
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand("brand");
////            productForm.setCategory("category");
////            productForm.setBarcode("barcode");
////            productForm.setMrp(5);
////            productForm.setName("name");
////            productDto.add(productForm);
////            productDto.add(productForm);
////            fail("Product dto adds duplicate product");
////        } catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: Barcode already exists";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////    }
////
////    @Test
////    public void testGetById() throws ApiException {
////        BrandPojo brandPojo = new BrandPojo();
////        brandPojo.setBrand("brand");
////        brandPojo.setCategory("category");
////        brandDao.insert(brandPojo);
////        ProductForm productForm = new ProductForm();
////        productForm.setBrand("  brand");
////        productForm.setCategory("category  ");
////        productForm.setBarcode("barcode");
////        productForm.setMrp(1);
////        productForm.setName("name");
////        productDto.add(productForm);
////        ProductData productData = productDto.get(1);
////        assertEquals("brand", productData.getBrand());
////        assertEquals(productForm.getCategory(), productData.getCategory());
////        assertEquals(productForm.getBarcode(), productData.getBarcode());
////        assertEquals(productForm.getName(), productData.getName());
////        assertEquals(productForm.getMrp(), productData.getMrp(), 0.01);
////    }
////
////    @Test
////    public void testGetAll() {
////
////    }
////
////    @Test
////    public void testUpdate() throws ApiException {
////        BrandPojo brandPojo = new BrandPojo();
////        brandPojo.setBrand("brand");
////        brandPojo.setCategory("category");
////        brandDao.insert(brandPojo);
////        ProductForm productForm = new ProductForm();
////        productForm.setBrand("brand");
////        productForm.setCategory("category");
////        productForm.setBarcode("barcode");
////        productForm.setMrp(1);
////        productForm.setName("name");
////        productDto.add(productForm);
////        ProductForm productForm1 = new ProductForm();
////        productForm1.setBrand("brand");
////        productForm1.setCategory("category");
////        productForm1.setBarcode("newbarcode");
////        productForm1.setMrp(2);
////        productForm1.setName("newname");
////        productDto.update(1, productForm1);
////        ProductData productData = productDto.get(1);
////        assertEquals(productForm1.getName(), productData.getName());
////        assertEquals(productForm1.getMrp(), productData.getMrp(), 0.01);
////        assertEquals(productForm1.getBrand(), productData.getBrand());
////        assertEquals(productForm1.getCategory(), productData.getCategory());
////        assertEquals(productForm.getBarcode(), productData.getBarcode());
////    }
////
////    @Test
////    public void testEmptyUpdate() {
////
////    }
////
////    @Test
////    public void testDelete() throws ApiException {
////        try {
////
////            BrandPojo brandPojo = new BrandPojo();
////            brandPojo.setBrand("brand");
////            brandPojo.setCategory("category");
////            brandDao.insert(brandPojo);
////            ProductForm productForm = new ProductForm();
////            productForm.setBrand("  brand");
////            productForm.setCategory("category  ");
////            productForm.setBarcode("barcode");
////            productForm.setMrp(1);
////            productForm.setName("name");
////            productDto.add(productForm);
////            productDto.delete(1);
////            ProductData productData = productDto.get(1);
////            fail("product dto delete method not able to delete correct entry");
////        }
////        catch (ApiException apiException) {
////            final String ERROR_MSG = "Error: product with given ID does not exist, id: 1";
////            assertEquals(ERROR_MSG, apiException.getMessage());
////        }
////
////    }
////
}
