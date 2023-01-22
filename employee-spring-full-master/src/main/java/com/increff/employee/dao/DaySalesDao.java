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

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(DaySalesPojo daySalesPojo) {
		entityManager.persist(daySalesPojo);
	}

//	@Transactional(rollbackFor = ApiException.class)
//	public int delete(int id) {
//		Query query = entityManager.createQuery(delete_id);
//		query.setParameter("id", id);
//		return query.executeUpdate();
//	}
//
//	@Transactional(readOnly = true)
//	public BrandPojo select(int id) {
//		TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
//		query.setParameter("id", id);
//		return getSingle(query);
//	}
//
//	@Transactional(readOnly = true)
//	public List<BrandPojo> selectAll() {
//		TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
//		return query.getResultList();
//	}
//
//	@Transactional(rollbackFor = ApiException.class)
//	public void update(BrandPojo brandPojo) {
//	}
//
//	@Transactional(readOnly = true)
//	public BrandPojo select(String brand, String category) {
//		TypedQuery<BrandPojo> query = getQuery(select_brand_category, BrandPojo.class);
//		query.setParameter("brand", brand);
//		query.setParameter("category", category);
//		return getSingle(query);
//	}

}
