//package com.pos.service;
//
//import com.pos.dao.ProductDao;
//import com.pos.pojo.ProductPojo;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.Assert.*;
//
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class ProductServiceTest extends AbstractUnitTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Test
//    public void testAdd() throws ApiException {
//        //test adding productPojo through service layer
//        ProductPojo productPojo = new ProductPojo();
//        productPojo.setBarcode("barcode");
//        productPojo.setMrp(5);
//        productPojo.setBrand_category(1);
//        productPojo.setName("name");
//        productService.add(productPojo);
//        ProductPojo productPojo1 = productService.get(1);
//        assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
//        assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
//        assertEquals(productPojo.getName(), productPojo1.getName());
//        assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
//    }
//
//    @Test
//    public void testGetById() {
//        //test retrieval of brandPojo
//        try {
//            ProductPojo productPojo = new ProductPojo();
//            productPojo.setBarcode("barcode");
//            productPojo.setMrp(5);
//            productPojo.setBrand_category(1);
//            productPojo.setName("name");
//            productService.add(productPojo);
//            ProductPojo productPojo1 = productService.get(1);
//            assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
//            assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
//            assertEquals(productPojo.getName(), productPojo1.getName());
//            assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
//        } catch (ApiException apiException) {
//            fail("Get by Id method not able to retrieve correct entry");
//        }
//    }
//
//    @Test
//    public void testGetAll() throws ApiException {
//        List<ProductPojo> productPojoList = new ArrayList<ProductPojo>();
//        for (int i = 1; i <= 3; i++) {
//            ProductPojo productPojo = new ProductPojo();
//            productPojo.setBrand_category(i);
//            productPojo.setMrp(i);
//            productPojo.setName("name" + i);
//            productPojo.setBarcode("barcode" + i);
//            productService.add(productPojo);
//            productPojoList.add(productPojo);
//        }
//        List<ProductPojo> insertedProductPojos = productService.getAll();
//        assertArrayEquals(productPojoList.toArray(), insertedProductPojos.toArray());
//    }
//
//    @Test
//    public void testUpdate() throws ApiException {
//        ProductPojo productPojo = new ProductPojo();
//        productPojo.setBarcode("barcode");
//        productPojo.setMrp(5);
//        productPojo.setBrand_category(1);
//        productPojo.setName("name");
//        productService.add(productPojo);
//        ProductPojo productPojo1 = new ProductPojo();
//        productPojo1.setName("new_name");
//        productPojo1.setMrp(6);
//        productPojo1.setBrand_category(2);
//        productPojo1.setBarcode("new_barcode");
//        productService.update(1, productPojo1);
//        ProductPojo updatedProductPojo = productService.get(1);
//        assertEquals(productPojo.getBarcode(), updatedProductPojo.getBarcode());
//        assertEquals(productPojo1.getMrp(), updatedProductPojo.getMrp(), 0.01);
//        assertEquals(productPojo1.getName(), updatedProductPojo.getName());
//        assertEquals(productPojo1.getBrand_category(), updatedProductPojo.getBrand_category());
//    }
//
//    @Test
//    public void testDelete() throws ApiException {
//        ProductPojo productPojo = new ProductPojo();
//        productPojo.setBarcode("barcode");
//        productPojo.setMrp(5);
//        productPojo.setBrand_category(1);
//        productPojo.setName("name");
//        productService.add(productPojo);
//        productService.delete(1);
//        ProductPojo productPojo1 = productDao.select(1);
//        if (Objects.isNull(productPojo1) == false) {
//            fail("product service delete: not able to delete the entry");
//        }
//    }
//
//    @Test
//    public void testGetByBarcode() {
//        //test retrieval of brandPojo
//        try {
//            ProductPojo productPojo = new ProductPojo();
//            productPojo.setBarcode("barcode");
//            productPojo.setMrp(5);
//            productPojo.setBrand_category(1);
//            productPojo.setName("name");
//            productService.add(productPojo);
//            ProductPojo productPojo1 = productService.get(productPojo.getBarcode());
//            assertEquals(productPojo.getBarcode(), productPojo1.getBarcode());
//            assertEquals(productPojo.getMrp(), productPojo1.getMrp(), 0.01);
//            assertEquals(productPojo.getName(), productPojo1.getName());
//            assertEquals(productPojo.getBrand_category(), productPojo1.getBrand_category());
//        } catch (ApiException apiException) {
//            fail("Get by Barcode method not able to retrieve correct entry");
//        }
//    }
//
//}