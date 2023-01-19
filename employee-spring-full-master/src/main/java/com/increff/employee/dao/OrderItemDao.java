package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;

@Repository
public class OrderItemDao extends AbstractDao {

	private static final String delete_id = "delete from OrderItemPojo p where id=:id";
	private static final String delete_orderId = "delete from OrderItemPojo p where orderId=:orderId";
	private static final String select_id = "select p from OrderItemPojo p where id=:id";
	private static final String select_all = "select p from OrderItemPojo p";
	private static final String select_orderId = "select p from OrderItemPojo p where orderId=:orderId";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(OrderItemPojo orderItemPojo) {
		entityManager.persist(orderItemPojo);
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = entityManager.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(rollbackFor = ApiException.class)
	public int deleteOrder(int orderId) {
		Query query = entityManager.createQuery(delete_orderId);
		query.setParameter("orderId", orderId);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> selectItems(int orderId) {
		TypedQuery<OrderItemPojo> query = getQuery(select_orderId, OrderItemPojo.class);
		query.setParameter("orderId", orderId);
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
		return query.getResultList();
	}

	public void update(OrderItemPojo orderItemPojo) {
	}

}
