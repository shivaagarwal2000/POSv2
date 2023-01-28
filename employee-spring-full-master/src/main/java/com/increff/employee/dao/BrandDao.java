package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;

@Repository
public class BrandDao extends AbstractDao {
//	Dao deals with executing the jpql queries, here for the BrandPojo Entity
	//TODO: refactor - major - clean up the code duplication and send class general query to abstractdao
	//TODO: uppercase for final
	private static final String DELETE_ID = "delete from BrandPojo p where id=:id";
	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_ALL = "select p from BrandPojo p";
	private static final String SELECT_BRAND_CATEGORY = "select p from BrandPojo p where brand=:brand and category=:category";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(BrandPojo brandPojo) {
		entityManager.persist(brandPojo);
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = entityManager.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
		return query.getResultList();
	}

	@Transactional(rollbackFor = ApiException.class) // TODO: refactor - mini: remove
	public void update(BrandPojo brandPojo) {
	}

	@Transactional(readOnly = true)
	public BrandPojo select(String brand, String category) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND_CATEGORY, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}

}
