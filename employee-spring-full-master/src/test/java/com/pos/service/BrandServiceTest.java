//package com.pos.service;
//
//import com.pos.pojo.BrandPojo;
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
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class BrandServiceTest extends AbstractUnitTest {
//
//    @Autowired
//    private BrandService brandService;
//
//    @Test
//    public void testAdd() throws ApiException {
//        //test adding brandPojo through service layer
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandService.add(brandPojo);
//        BrandPojo brandPojo1 = brandService.get(brandPojo.getBrand(), brandPojo.getCategory());
//        assertEquals(brandPojo.getBrand(), brandPojo1.getBrand());
//        assertEquals(brandPojo.getCategory(), brandPojo1.getCategory());
//
//    }
//
//    @Test
//    public void testGetId() {
//        //test retrieval of brandPojo through service layer
//        try {
//            BrandPojo brandPojo = new BrandPojo();
//            String brand = "brand";
//            String category = "category";
//            brandPojo.setBrand(brand);
//            brandPojo.setCategory(category);
//            brandService.add(brandPojo);
//            BrandPojo brandPojo1 = brandService.get(1);
//            assertEquals(brand, brandPojo1.getBrand());
//            assertEquals(category, brandPojo1.getCategory());
//        } catch (ApiException apiException) {
//            fail("Get by Id method not able to retrieve correct entry");
//        }
//    }
//
//    @Test
//    public void testGetAll() throws ApiException {
//        List<BrandPojo> brandPojoList = new ArrayList<BrandPojo>();
//        for (int i = 1; i <= 3; i++) {
//            BrandPojo brandPojo = new BrandPojo();
//            brandPojo.setBrand("brand" + i);
//            brandPojo.setCategory("category" + i);
//            brandPojoList.add(brandPojo);
//            brandService.add(brandPojo);
//        }
//        List<BrandPojo> insertedBrandPojos = brandService.getAll();
//        assertArrayEquals(brandPojoList.toArray(), insertedBrandPojos.toArray());
//
//    }
//
//    @Test
//    public void testUpdate() throws ApiException {
//        //test if brand service is able to update the entry
//        BrandPojo brandPojo = new BrandPojo();
//        String brand = "brand";
//        String category = "category";
//        brandPojo.setBrand(brand);
//        brandPojo.setCategory(category);
//        brandService.add(brandPojo);
//        BrandPojo brandPojo1 = new BrandPojo();
//        String newBrand = "newbrand";
//        String newCategory = "newcategory";
//        brandPojo1.setBrand(newBrand);
//        brandPojo1.setCategory(newCategory);
//        brandService.update(1, brandPojo1);
//        BrandPojo updatedBrandPojo = brandService.get(1);
//        assertEquals(newBrand, updatedBrandPojo.getBrand());
//        assertEquals(newCategory, updatedBrandPojo.getCategory());
//
//    }
//
//    @Test
//    public void testDelete() throws ApiException {
//        //test if service layer able to delete pojo
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setBrand("brand");
//        brandPojo.setCategory("category");
//        brandService.add(brandPojo);
//        brandService.delete(1);
//        BrandPojo brandPojo1 = brandService.get(brandPojo.getBrand(), brandPojo.getCategory());
//        if (Objects.isNull(brandPojo1) == false){
//            fail("Delete service method not able to delete the entry");
//        }
//    }
//
//    @Test
//    public void testGetBrandCategory() throws ApiException {
//        //test if able to retrieve brandpojo by brand, category combination
//        BrandPojo brandPojo = new BrandPojo();
//        String brand = "brand";
//        String category = "category";
//        brandPojo.setBrand(brand);
//        brandPojo.setCategory(category);
//        brandService.add(brandPojo);
//        BrandPojo brandPojo1 = brandService.get(brand, category);
//        assertEquals(brand, brandPojo1.getBrand());
//        assertEquals(category, brandPojo1.getCategory());
//    }
//
//}
