package com.pos.dto;

import com.pos.helper.DtoTestHelper;
import com.pos.model.data.BrandData;
import com.pos.model.forms.BrandForm;
import com.pos.service.AbstractUnitTest;
import com.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        //test whether valid brand getting added
        BrandForm brandForm = DtoTestHelper.createBrandForm("  Brand-test  ", "brand_category");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        assertEquals(brandData.getBrand(), "brand-test");
        assertEquals(brandData.getCategory(), brandForm.getCategory());
    }

    @Test(expected = ApiException.class)// TODO add expected in other cases also Priority: 5
    public void testEmptyBrandAdd() throws ApiException {
        //test whether empty brand showing error
        try {
            BrandForm brandForm = DtoTestHelper.createBrandForm("", "category-test");
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: either of brand/category can not be empty";
            assertEquals(apiException.getMessage(), ERROR_MSG);
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testEmptyCategoryAdd() throws ApiException {
        //test whether empty category showing error
        try {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand-test", "");
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            //TODO: refactor - mini - make these strings available throughout
            final String ERROR_MSG = "Error: either of brand/category can not be empty";
            assertEquals(apiException.getMessage(), ERROR_MSG);
            throw apiException;
        }

    }

    @Test(expected = ApiException.class)
    public void testDuplicateAdd() throws ApiException {
        //test if duplicate brand category combination is getting added
        try {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: given brand, category combination already exists";
            assertEquals(apiException.getMessage(), ERROR_MSG);
            throw apiException;
        }
    }

    @Test
    public void testCategoryLengthOnAdd() {
        //test maximum length limit of category
        try {
            int length = 52;
            String category = StringUtils.repeat("c", length);
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand-test", category);
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: length of brand, category exceeds maximum limit";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }
    }

    @Test
    public void testBrandLengthOnAdd() {
        //test maximum length limit of brand
        try {
            int length = 52;
            String brand = StringUtils.repeat("b", length);
            BrandForm brandForm = DtoTestHelper.createBrandForm(brand, "category-test");
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: length of brand, category exceeds maximum limit";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }
    }

    @Test
    public void testGet() {
        //test the retrieval of single item
        try {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            BrandData brandData = brandDto.get(1);
            assertEquals(brandForm.getBrand(), brandData.getBrand());
            assertEquals(brandForm.getCategory(), brandData.getCategory());
        } catch (ApiException apiException) {
            fail("Get method not able to retrieve correct entry");
        }
    }

    @Test
    public void testBulkAdd() throws ApiException {
        List<BrandData> brandDataList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand" + i, "category" + i);
            BrandData brandData = new BrandData();
            brandData.setBrand(brandForm.getBrand());
            brandData.setCategory(brandForm.getCategory());
            brandData.setId(i);
            brandFormList.add(brandForm);
            brandDataList.add(brandData);
        }
        brandDto.bulkAdd(brandFormList);
        List<BrandData> insertedBrandData = brandDto.getAll();
        assertArrayEquals(brandDataList.toArray(), insertedBrandData.toArray());
    }

    @Test
    public void testGetAll() throws ApiException {
        List<BrandData> brandDataList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand" + i, "category" + i);
            brandDto.add(brandForm);
            BrandData brandData = new BrandData();
            brandData.setBrand(brandForm.getBrand());
            brandData.setCategory(brandForm.getCategory());
            brandData.setId(i);
            brandDataList.add(brandData);
        }
        List<BrandData> insertedBrandData = brandDto.getAll();
        assertArrayEquals(brandDataList.toArray(), insertedBrandData.toArray());
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandForm brandForm = DtoTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        BrandForm newBrandForm = DtoTestHelper.createBrandForm("new_brand", "new_category");
        brandDto.update(1, newBrandForm);
        BrandData updatedBrandData = brandDto.get(1);
        assertEquals(newBrandForm.getBrand(), updatedBrandData.getBrand());
        assertEquals(newBrandForm.getCategory(), updatedBrandData.getCategory());
        assertEquals(1, updatedBrandData.getId());
    }

    @Test
    public void testDelete() {
        //test if brand is getting deleted
        try {
            BrandForm brandForm = DtoTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            brandDto.delete(1);
            BrandData brandData = brandDto.get(1);
            fail("Delete method not deleting the entry");
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Brand with given ID does not exit, id: 1";
            assertEquals(ERROR_MSG, apiException.getMessage());
        }
    }

    @Test
    public void testValidate() {
        //test validating the length while adding
        try {
            int length = 52;
            String brand = StringUtils.repeat("b", length);
            BrandForm brandForm = new BrandForm();
            brandForm.setBrand(brand);
            brandForm.setCategory("category-test");
            brandDto.validate(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: length of brand, category exceeds maximum limit";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }
    }

}
