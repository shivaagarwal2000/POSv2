package com.pos.service;

import com.pos.dao.BrandDao;
import com.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(BrandPojo brandPojo) throws ApiException {
        //add a new brand
        brandDao.insert(brandPojo);
    }

    @Transactional(readOnly = true)
    public BrandPojo get(int id) throws ApiException {
        //fetch a brand with brandId
        BrandPojo brandPojo = brandDao.select(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return brandPojo;
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> getAll() {
        //fetch all the brands
        return brandDao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, BrandPojo brandPojo) throws ApiException {
        //update an old brand
        BrandPojo oldBrandPojo = get(id);
        oldBrandPojo.setBrand(brandPojo.getBrand());
        oldBrandPojo.setCategory(brandPojo.getCategory());
        brandDao.update(oldBrandPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void delete(int id) {
        //delete an existing brand
        brandDao.delete(id);
    }

    @Transactional(readOnly = true)
    public BrandPojo get(String brand, String category) {
        //fetch a brand with brand, category combination
        return brandDao.select(brand, category);
    }

}
