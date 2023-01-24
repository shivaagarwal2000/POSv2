package com.increff.employee.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;

@Repository
public class OrderDao extends AbstractDao {

	private static final String delete_id = "delete from OrderPojo p where id=:id";
	private static final String select_id = "select p from OrderPojo p where id=:id";
	private static final String select_date = "select p from OrderPojo p where time LIKE :reqDate";
	private static final String select_orderTime = "select p from OrderPojo p where time=:time";
	private static final String select_all_orderDate = "select p from OrderPojo p where time >= :startDate and time <= :endDate";
	private static final String select_all = "select p from OrderPojo p";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo insert(OrderPojo orderPojo) {
//		add pending order in the orderPojo
		entityManager.persist(orderPojo);
//		TODO: print the id to check what is getting returned
		return orderPojo;
	}

	//TODO: delete this
	@Transactional(readOnly = true)
	public OrderPojo select(ZonedDateTime orderTime) {
		TypedQuery<OrderPojo> query = getQuery(select_orderTime, OrderPojo.class);
		query.setParameter("time", orderTime);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public OrderPojo select(int orderId) {
		TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
		query.setParameter("id", orderId);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> selectByDate(String reqDate) {
		TypedQuery<OrderPojo> query = getQuery(select_date, OrderPojo.class);
		query.setParameter("reqDate",reqDate + "%");
		return query.getResultList();
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = entityManager.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(OrderPojo orderPojo){

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
	public List<OrderPojo> selectBetweeenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		TypedQuery<OrderPojo> query = getQuery(select_all_orderDate, OrderPojo.class);
		return query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void updateStatus(OrderPojo orderPojo){
	}
//
//	public void update(OrderItemPojo p) {
//	}

}
