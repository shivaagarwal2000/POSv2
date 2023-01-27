package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.forms.BrandForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        //test whether valid brand getting added
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand-test");
        brandForm.setCategory("brand_category");
        brandDto.add(brandForm);
    }

    @Test
    public void testEmptyBrandAdd() {
        //test whether empty brand showing error
        try {
            BrandForm brandForm = new BrandForm();
            brandForm.setCategory("category-test");
            brandDto.add(brandForm);
            fail("empty brand --> empty add shouldv'e thrown exception but didn't");
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: either of brand/category can not be empty";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }

    }

    @Test
    public void testEmptyCategoryAdd() throws ApiException {
        //test whether empty category showing error
        try {
            BrandForm brandForm = new BrandForm();
            brandForm.setBrand("brand-test");
            brandDto.add(brandForm);
            fail("empty category --> empty add shouldv'e thrown exception but didn't");
        } catch (final ApiException apiException) {
            //TODO: refactor - mini - make these strings available throughout
            final String ERROR_MSG = "Error: either of brand/category can not be empty";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }

    }

    @Test
    public void testDuplicateAdd() {
        //test if duplicate brand category combination is getting added
        try {
            BrandForm brandForm = new BrandForm();
            BrandForm brandForm1 = new BrandForm();
            brandForm.setBrand("brand-test");
            brandForm.setCategory("category-test");
            brandForm1.setBrand("brand-test");
            brandForm1.setCategory("category-test");
            brandDto.add(brandForm);
            brandDto.add(brandForm1);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: given brand, category combination already exists";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }
    }

    @Test
    public void testCategoryLengthOnAdd() {
        //test maximum length limit of category
        try {
            int length = 52;
            String category = StringUtils.repeat("c", length);
            BrandForm brandForm = new BrandForm();
            brandForm.setBrand("brand-test");
            brandForm.setCategory(category);
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
            BrandForm brandForm = new BrandForm();
            brandForm.setBrand(brand);
            brandForm.setCategory("category-test");
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: length of brand, category exceeds maximum limit";
            assertEquals(apiException.getMessage(), ERROR_MSG);
        }
    }

    @Test
    public void testGet() {
        //test the retrieval of single item
//        try {
//            BrandForm brandForm = new BrandForm();
//            String brand = "brand";
//            String category = "category";
//            brandForm.setCategory(category);
//            brandForm.setBrand(brand);
//            brandDto.add(brandForm);
//            BrandData brandData = brandDto.get(1);
//            assertEquals(brand, brandData.getBrand());
//            assertEquals(category, brandData.getCategory());
//        }
//        catch (ApiException apiException) {
//            fail("Get method not able to retrieve correct entry");
//        }
    }

    @Test
    public void testGetAll(){
        //test the retrieval of all entries
//        try {
//            List<BrandData>
//        }
//        catch () {
//
//        }
    }

}
