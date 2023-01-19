package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;

@Repository
public class OrderDao extends AbstractDao {

	private static final String delete_id = "delete from OrderPojo p where id=:id";
	private static final String select_id = "select p from OrderPojo p where id=:id";
	private static final String select_orderTime = "select p from OrderPojo p where orderTime=:orderTime";
	private static final String select_all_orderDate = "select p from OrderPojo p where orderTime >= :startDate and orderTime <= :endDate";
	private static final String select_all = "select p from OrderPojo p";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(OrderPojo orderPojo) {
		entityManager.persist(orderPojo);
	}

	@Transactional(readOnly = true)
	public OrderPojo select(String orderTime) {
		TypedQuery<OrderPojo> query = getQuery(select_orderTime, OrderPojo.class);
		query.setParameter("orderTime", orderTime);
		return getSingle(query);
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = entityManager.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

//
//	public OrderItemPojo select(int id) {
//		TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
//		query.setParameter("id", id);
//		return getSingle(query);
//	}
//
	@Transactional(readOnly = true)
	public List<OrderPojo> selectAll() {
		TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> selectBetweeenDates(String startDate, String endDate) {
		TypedQuery<OrderPojo> query = getQuery(select_all_orderDate, OrderPojo.class);
		return query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
	}
//
//	public void update(OrderItemPojo p) {
//	}

}
