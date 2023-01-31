//package com.pos.dto;
//
//import com.pos.dao.BrandDao;
//import com.pos.dao.ProductDao;
//import com.pos.model.data.InventoryData;
//import com.pos.model.data.InventoryReportData;
//import com.pos.model.forms.InventoryForm;
//import com.pos.model.forms.ProductForm;
//import com.pos.pojo.BrandPojo;
//import com.pos.pojo.ProductPojo;
//import com.pos.service.AbstractUnitTest;
//import com.pos.service.ApiException;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class InventoryDtoTest extends AbstractUnitTest {
//
//    @Autowired
//    private BrandDao brandDao;
//    @Autowired
//    private InventoryDto inventoryDto;
//    @Autowired
//    private ProductDto productDto;
//    @Autowired
//    private ProductDao productDao;
//
//    @Test
//    public void testAdd() throws ApiException {
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandDao.insert(brandPojo);
//        ProductForm productForm = new ProductForm();
//        productForm.setBrand("brand");
//        productForm.setCategory("category");
//        productForm.setBarcode("barcode");
//        productForm.setMrp(1);
//        productForm.setName("name");
//        productDto.add(productForm);
//        InventoryForm inventoryForm = new InventoryForm();
//        inventoryForm.setBarcode("  Barcode ");
//        inventoryForm.setQuantity(3);
//        inventoryDto.add(inventoryForm);
//        ProductPojo productPojo = productDao.select(productForm.getBarcode());
//        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
//        assertEquals(productForm.getBarcode(), inventoryData.getBarcode());
//        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
//    }
//
//    @Test
//    public void testAddWithoutBarcode(){
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand");
//            brandPojo.setCategory("category");
//            brandDao.insert(brandPojo);
//            ProductForm productForm = new ProductForm();
//            productForm.setBrand("brand");
//            productForm.setCategory("category");
//            productForm.setBarcode("barcode");
//            productForm.setMrp(1);
//            productForm.setName("name");
//            productDto.add(productForm);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setQuantity(3);
//            inventoryDto.add(inventoryForm);
//            fail("inventory dto adding entry without barcode");
//        }
//        catch (ApiException apiException) {
//            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
//            assertEquals(ERROR_MSG, apiException.getMessage());
//        }
//
//    }
//
//    @Test
//    public void testAddWithoutQuantity(){
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand");
//            brandPojo.setCategory("category");
//            brandDao.insert(brandPojo);
//            ProductForm productForm = new ProductForm();
//            productForm.setBrand("brand");
//            productForm.setCategory("category");
//            productForm.setBarcode("barcode");
//            productForm.setMrp(1);
//            productForm.setName("name");
//            productDto.add(productForm);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setBarcode(productForm.getBarcode());
//            inventoryDto.add(inventoryForm);
//            fail("inventory dto adding entry without quantity");
//        }
//        catch (ApiException apiException) {
//            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
//            assertEquals(ERROR_MSG, apiException.getMessage());
//        }
//    }
//
//    @Test
//    public void testAddWithoutProduct(){
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand");
//            brandPojo.setCategory("category");
//            brandDao.insert(brandPojo);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setQuantity(4);
//            inventoryForm.setBarcode("barcode");
//            inventoryDto.add(inventoryForm);
//            fail("inventory dto adding entry without product");
//        }
//        catch (ApiException apiException) {
//            final String ERROR_MSG = "Error: product with given barcode does not exist";
//            assertEquals(ERROR_MSG, apiException.getMessage());
//        }
//    }
//
//    @Test
//    public void testGetById() throws ApiException {
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandDao.insert(brandPojo);
//        ProductForm productForm = new ProductForm();
//        productForm.setBrand("brand");
//        productForm.setCategory("category");
//        productForm.setBarcode("barcode");
//        productForm.setMrp(1);
//        productForm.setName("name");
//        productDto.add(productForm);
//        InventoryForm inventoryForm = new InventoryForm();
//        inventoryForm.setBarcode(productForm.getBarcode());
//        inventoryForm.setQuantity(3);
//        inventoryDto.add(inventoryForm);
//        ProductPojo productPojo = productDao.select(productForm.getBarcode());
//        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
//        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
//        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
//    }
//
//    @Test
//    public void testGetAll() throws ApiException { // TODO: write test
//        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandDao.insert(brandPojo);
//        for (int i = 1; i <= 3; i++){
//            ProductForm productForm = new ProductForm();
//            productForm.setBrand("brand");
//            productForm.setCategory("category");
//            productForm.setBarcode("barcode" + i);
//            productForm.setMrp(i);
//            productForm.setName("name" + i);
//            productDto.add(productForm);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setBarcode(productForm.getBarcode());
//            inventoryForm.setQuantity(i);
//            inventoryDto.add(inventoryForm);
//            InventoryData inventoryData = new InventoryData();
//            inventoryData.setId(i);
//            inventoryData.setQuantity(inventoryForm.getQuantity());
//            inventoryData.setBarcode(productForm.getBarcode());
//            inventoryDataList.add(inventoryData);
//        }
//
//        List<InventoryData> insertedInventoryDatas = inventoryDto.getAll();
//        assertArrayEquals( inventoryDataList.toArray(), insertedInventoryDatas.toArray());
//    }
//
//    @Test
//    public void testGetReport() throws ApiException {
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandDao.insert(brandPojo);
//        InventoryReportData inventoryReportData = new InventoryReportData();
//        inventoryReportData.setBrand(brandPojo.getBrand());
//        inventoryReportData.setCategory(brandPojo.getCategory());
//        inventoryReportData.setQuantity(0);
//        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
//        for (int i = 1; i <= 2; i++){
//            ProductForm productForm = new ProductForm();
//            productForm.setBrand(brandPojo.getBrand());
//            productForm.setCategory(brandPojo.getCategory());
//            productForm.setBarcode("barcode" + i);
//            productForm.setMrp(i);
//            productForm.setName("name" + i);
//            productDto.add(productForm);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setBarcode(productForm.getBarcode());
//            inventoryForm.setQuantity(i);
//            inventoryDto.add(inventoryForm);
//            inventoryReportData.setQuantity(inventoryReportData.getQuantity() + inventoryForm.getQuantity());
//        }
//        inventoryReportDataList.add(inventoryReportData);
//        List<InventoryReportData> insertedInventoryReportData = inventoryDto.getReportData();
//        assertArrayEquals(inventoryReportDataList.toArray(), insertedInventoryReportData.toArray());
//    }
//
//    @Test
//    public void testUpdate() throws ApiException {
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        System.out.println(brandDao);
//        brandDao.insert(brandPojo);
//        ProductForm productForm = new ProductForm();
//        productForm.setBrand("brand");
//        productForm.setCategory("category");
//        productForm.setBarcode("barcode");
//        productForm.setMrp(1);
//        productForm.setName("name");
//        productDto.add(productForm);
//        InventoryForm inventoryForm = new InventoryForm();
//        inventoryForm.setBarcode(productForm.getBarcode());
//        inventoryForm.setQuantity(3);
//        inventoryDto.add(inventoryForm);
//        ProductPojo productPojo = productDao.select(productForm.getBarcode());
//        inventoryForm.setQuantity(5);
//        inventoryDto.update(productPojo.getId(), inventoryForm);
//        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
//        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
//        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
//    }
//
//    @Test
//    public void testDelete() {
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand");
//            brandPojo.setCategory("category");
//            System.out.println(brandDao);
//            brandDao.insert(brandPojo);
//            ProductForm productForm = new ProductForm();
//            productForm.setBrand("brand");
//            productForm.setCategory("category");
//            productForm.setBarcode("barcode");
//            productForm.setMrp(1);
//            productForm.setName("name");
//            productDto.add(productForm);
//            InventoryForm inventoryForm = new InventoryForm();
//            inventoryForm.setBarcode(productForm.getBarcode());
//            inventoryForm.setQuantity(3);
//            inventoryDto.add(inventoryForm);
//            ProductPojo productPojo = productDao.select(productForm.getBarcode());
//            inventoryDto.delete(productPojo.getId());
//            InventoryData inventoryData = inventoryDto.get(productPojo.getId());
//            fail("inventory dto unable to delete entry");
//        }
//        catch (ApiException apiException) {
//            final String ERROR_MSG = "Error: Inventory with given ID does not exit, id: 1";
//            assertEquals(ERROR_MSG, apiException.getMessage());
//        }
//    }
//
//}
