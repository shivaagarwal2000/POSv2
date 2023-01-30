package com.pos.dto;

import com.pos.model.data.BrandData;
import com.pos.model.forms.BrandForm;
import com.pos.service.AbstractUnitTest;
import com.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.thymeleaf.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        //test whether valid brand getting added
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("  Brand-test  ");
        brandForm.setCategory("brand_category");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        assertEquals(brandData.getBrand(), "brand-test");
        assertEquals(brandData.getCategory(), brandForm.getCategory());
    }

    @Test(expected = ApiException.class)// TODO add expected in other cases also Priority: 5
    public void testEmptyBrandAdd() throws ApiException{
        //test whether empty brand showing error
        try {
            BrandForm brandForm = new BrandForm();
            brandForm.setCategory("category-test");
            brandDto.add(brandForm);
        } catch (final ApiException apiException) {
            final String ERROR_MSG = "Error: either of brand/category can not be empty";
            assertEquals(apiException.getMessage(), ERROR_MSG);
            throw apiException;
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

    @Test(expected = ApiException.class)
    public void testDuplicateAdd() throws ApiException{
        //test if duplicate brand category combination is getting added
        try {
            BrandForm brandForm = new BrandForm();
            String brand = "brand";
            String category = "category";
            brandForm.setBrand(brand);
            brandForm.setCategory(category);
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
        try {
            BrandForm brandForm = new BrandForm();
            String brand = "brand";
            String category = "category";
            brandForm.setCategory(category);
            brandForm.setBrand(brand);
            brandDto.add(brandForm);
//            List<BrandData> brandDataList = brandDto.getAll();
//            for (BrandData brandData: brandDataList) {
//                System.out.println(brandData.getBrand() + brandData.getCategory() + brandData.getId());
//            }
            BrandData brandData = brandDto.get(1);
            assertEquals(brand, brandData.getBrand());
            assertEquals(category, brandData.getCategory());
        } catch (ApiException apiException) {
            fail("Get method not able to retrieve correct entry");
        }
    }

    @Test
    public void testGetAll() {
        //test the retrieval of all entries
//        try {
//            List<BrandData>
//        }
//        catch () {
//
//        }
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {
        //test if brand is getting deleted
        try {
            BrandForm brandForm = new BrandForm();
            String brand = "brand";
            String category = "category";
            brandForm.setCategory(category);
            brandForm.setBrand(brand);
            brandDto.add(brandForm);
//            List<BrandData> brandDataList = brandDto.getAll();
//            for (BrandData brandData: brandDataList) {
//                System.out.println(brandData.getBrand() + brandData.getCategory() + brandData.getId());
//            }
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
