package com.pos.service;

import com.pos.helper.posTestHelper;
import com.pos.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BrandServiceTest extends AbstractUnitTest {

    @Autowired
    private BrandService brandService;

    @Test
    public void testAdd() throws ApiException {
        //test adding brandPojo through service layer
        BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
        brandService.add(brandPojo);
        BrandPojo brandPojo1 = brandService.get(brandPojo.getBrand(), brandPojo.getCategory());
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());

    }

    @Test
    public void testGetId() throws ApiException {
        //test retrieval of brandPojo through service layer
        BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
        brandService.add(brandPojo);
        BrandPojo brandPojo1 = brandService.get(1);
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }

    @Test(expected = ApiException.class)
    public void testGetFalseId() throws ApiException {
        //test retrieval of brandPojo through service layer
        try {
            BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
            brandService.add(brandPojo);
            BrandPojo brandPojo1 = brandService.get(2);
            fail("get not throwing exception on no match");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Brand with given ID does not exit, id: 2";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }


    @Test
    public void testGetAll() throws ApiException {
        List<BrandPojo> brandPojoList = new ArrayList<BrandPojo>();
        for (int i = 1; i <= 3; i++) {
            BrandPojo brandPojo = posTestHelper.createBrandPojo("brand" + i, "category" + i);
            brandPojoList.add(brandPojo);
            brandService.add(brandPojo);
        }
        List<BrandPojo> insertedBrandPojos = brandService.getAll();
        assertArrayEquals(brandPojoList.toArray(), insertedBrandPojos.toArray());

    }

    @Test
    public void testUpdate() throws ApiException {
        //test if brand service is able to update the entry
        BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
        brandService.add(brandPojo);
        BrandPojo brandPojo1 = posTestHelper.createBrandPojo("newbrand", "newcategory");
        brandService.update(1, brandPojo1);
        BrandPojo updatedBrandPojo = brandService.get(1);
        assertEquals(brandPojo1.getBrand(), updatedBrandPojo.getBrand());
        assertEquals(brandPojo1.getCategory(), updatedBrandPojo.getCategory());

    }

    @Test
    public void testDelete() throws ApiException {
        //test if service layer able to delete pojo
        BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
        brandService.add(brandPojo);
        brandService.delete(1);
        BrandPojo brandPojo1 = brandService.get(brandPojo.getBrand(), brandPojo.getCategory());
        if (Objects.isNull(brandPojo1) == false){
            fail("Delete service method not able to delete the entry");
        }
    }

    @Test
    public void testGetBrandCategory() throws ApiException {
        //test if able to retrieve brandpojo by brand, category combination
        BrandPojo brandPojo = posTestHelper.createBrandPojo("brand", "category");
        brandService.add(brandPojo);
        BrandPojo brandPojo1 = brandService.get(brandPojo.getBrand(), brandPojo.getCategory());
        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
    }
}
