package com.pos.dto;

import com.pos.dao.ProductDao;
import com.pos.helper.posTestHelper;
import com.pos.model.data.InventoryData;
import com.pos.model.data.InventoryReportData;
import com.pos.model.forms.BrandForm;
import com.pos.model.forms.InventoryForm;
import com.pos.model.forms.ProductForm;
import com.pos.service.AbstractUnitTest;
import org.commons.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventoryDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;
    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
        productDto.add(productForm);
        InventoryForm inventoryForm = posTestHelper.createInventoryForm("  Barcode", 5);
        inventoryDto.add(inventoryForm);
        InventoryData inventoryData = inventoryDto.get(1);
        assertEquals(productForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyBarcode() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm("", 5);
            inventoryDto.add(inventoryForm);
            fail("inventory dto adding entry without barcode");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyQuantity() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode", null);
            inventoryDto.add(inventoryForm);
            fail("inventory dto adding entry without quantity");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutProduct() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode", 5);
            inventoryDto.add(inventoryForm);
            fail("inventory dto adding entry without product");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product does not exists for barcode: barcode";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testDuplicateAdd() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode", 5);
            inventoryDto.add(inventoryForm);
            inventoryForm.setQuantity(10);
            inventoryDto.add(inventoryForm);
            fail("inventory dto adding duplicate entries");
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
        InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode", 5);
        inventoryDto.add(inventoryForm);
        InventoryData inventoryData = inventoryDto.get(1);
        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test
    public void testGetAll() throws ApiException {
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        for (int i = 1; i <= 3; i++) {
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name" + i, "barcode" + i, 5.5);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode" + i, i);
            inventoryDto.add(inventoryForm);
            InventoryData inventoryData = posTestHelper.createInventoryData(inventoryForm.getBarcode(), inventoryForm.getQuantity(), i);
            inventoryDataList.add(inventoryData);
        }
        List<InventoryData> insertedInventoryDatas = inventoryDto.getAll();
        for (int i = 1; i <= 3; i++) {
            assertEquals(inventoryDataList.get(i - 1).getId(), insertedInventoryDatas.get(i - 1).getId());
            assertEquals(inventoryDataList.get(i - 1).getBarcode(), insertedInventoryDatas.get(i - 1).getBarcode());
            assertEquals(inventoryDataList.get(i - 1).getQuantity(), insertedInventoryDatas.get(i - 1).getQuantity());
        }
    }

    @Test
    public void testGetReport() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        InventoryReportData inventoryReportData = new InventoryReportData();
        inventoryReportData.setBrand(brandForm.getBrand());
        inventoryReportData.setCategory(brandForm.getCategory());
        inventoryReportData.setQuantity(0);
        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name" + i, "barcode" + i, 5.5);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm(productForm.getBarcode(), i);
            inventoryDto.add(inventoryForm);
            inventoryReportData.setQuantity(inventoryReportData.getQuantity() + inventoryForm.getQuantity());
        }
        inventoryReportDataList.add(inventoryReportData);
        List<InventoryReportData> insertedInventoryReportData = inventoryDto.getReportData();
        assertArrayEquals(inventoryReportDataList.toArray(), insertedInventoryReportData.toArray());
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
        productDto.add(productForm);
        InventoryForm inventoryForm = posTestHelper.createInventoryForm("barcode", 5);
        inventoryDto.add(inventoryForm);
        inventoryForm.setQuantity(5);
        inventoryDto.update(1, inventoryForm);
        InventoryData inventoryData = inventoryDto.get(1);
        assertEquals(inventoryForm.getBarcode(), inventoryData.getBarcode());
        assertEquals(inventoryForm.getQuantity(), inventoryData.getQuantity());
    }

    @Test(expected = ApiException.class)
    public void testDelete() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name", "barcode", 5.00);
            productDto.add(productForm);
            InventoryForm inventoryForm = posTestHelper.createInventoryForm(productForm.getBarcode(), 5);
            inventoryDto.add(inventoryForm);
            inventoryDto.delete(1);
            InventoryData inventoryData = inventoryDto.get(1);
            fail("inventory dto unable to delete entry");
        } catch (ApiException apiException) {
            final String ERROR_MSG = "Error: Inventory with given ID does not exit, id: 1";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }
}
