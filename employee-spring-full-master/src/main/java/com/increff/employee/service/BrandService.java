package com.increff.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(BrandPojo brandPojo) throws ApiException {
		brandDao.insert(brandPojo);
	}

	@Transactional(readOnly = true)
	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> getAll() {
		return brandDao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, BrandPojo brandPojo) throws ApiException {
		BrandPojo oldBrandPojo = getCheck(id);
		oldBrandPojo.setBrand(brandPojo.getBrand());
		oldBrandPojo.setCategory(brandPojo.getCategory());
		brandDao.update(oldBrandPojo);
	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) {
		brandDao.delete(id);
	}

	@Transactional(readOnly = true)
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo brandPojo = brandDao.select(id);
		if (brandPojo == null) {
			throw new ApiException("Brand with given ID does not exit, id: " + id);
		}
		return brandPojo;
	}

	@Transactional(readOnly = true)
	public BrandPojo get(String brand, String category) {
		return brandDao.select(brand, category);

	}

}
