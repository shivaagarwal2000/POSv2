package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;

@Repository
public class ProductDao extends AbstractDao {

	private static final String delete_id = "delete from ProductPojo p where id=:id";
	private static final String select_id = "select p from ProductPojo p where id=:id";
	private static final String select_all = "select p from ProductPojo p";
	private static final String select_barcode = "select p from ProductPojo p where barcode=:barcode";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(ProductPojo productPojo) {
		entityManager.persist(productPojo);
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = entityManager.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(select_barcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
		return query.getResultList();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(ProductPojo productPojo) {
	}

}
