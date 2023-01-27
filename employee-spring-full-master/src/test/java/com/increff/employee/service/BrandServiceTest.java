package com.increff.employee.service;

import com.increff.employee.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BrandServiceTest extends AbstractUnitTest {

    @Autowired
    private BrandService brandService;

    @Test
    public void testAdd() throws ApiException {
        //test adding brandPojo
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandService.add(brandPojo);
    }

    @Test
    public void testGet() {
        //test retrieval of brandPojo
        try {
            BrandPojo brandPojo = new BrandPojo();
            String brand = "brand";
            String category = "category";
            brandPojo.setBrand(brand);
            brandPojo.setCategory(category);
            brandService.add(brandPojo);
            BrandPojo brandPojo1 = brandService.get(1);
            assertEquals(brand, brandPojo1.getBrand());
            assertEquals(category, brandPojo1.getCategory());
        } catch (ApiException apiException) {
            fail("Get method not able to retrieve correct entry");
        }
    }

    @Test
    public void testGetAll() {
        //test retrieving all BrandPojo
//        try {
//            List<BrandData>
//        }
//        catch () {
//
//        }

    }
}
