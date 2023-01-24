package com.increff.employee.dao;

import com.increff.employee.pojo.DaySalesPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DaySalesDao extends AbstractDao {

//	private static final String delete_id = "delete from BrandPojo p where id=:id";
//	private static final String select_id = "select p from BrandPojo p where id=:id";
//	private static final String select_all = "select p from BrandPojo p";
//	private static final String select_brand_category = "select p from BrandPojo p where brand=:brand and category=:category";
//TODO: clean up
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(DaySalesPojo daySalesPojo) {
		entityManager.persist(daySalesPojo);
	}

}
