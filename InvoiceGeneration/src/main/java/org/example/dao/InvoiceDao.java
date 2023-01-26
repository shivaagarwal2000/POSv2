package org.example.dao;

import org.example.pojo.InvoicePojo;
import org.example.service.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class InvoiceDao extends AbstractDao {

	private static final String select_orderId = "select p from InvoicePojo p where orderId=:orderId";
//	private static final String select_all = "select p from BrandPojo p";
//	private static final String select_brand_category = "select p from BrandPojo p where brand=:brand and category=:category";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(InvoicePojo invoicePojo) {
		entityManager.persist(invoicePojo);
	}

	@Transactional(readOnly = true)
	public InvoicePojo select(int orderId) {
		TypedQuery<InvoicePojo> query = getQuery(select_orderId, InvoicePojo.class);
		query.setParameter("orderId", orderId);
		return getSingle(query);
	}

}
