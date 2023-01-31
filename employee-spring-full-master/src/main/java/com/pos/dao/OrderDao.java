package com.pos.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.pos.service.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pos.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

	private static final String delete_id = "delete from OrderPojo p where id=:id";
	private static final String select_id = "select p from OrderPojo p where id=:id";
	private static final String select_all_orderDate = "select p from OrderPojo p where time >= :startDate and time <= :endDate and status = " + "placed";
	private static final String select_all = "select p from OrderPojo p";

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo insert(OrderPojo orderPojo) {
		//add pending order in the orderPojo
		entityManager.persist(orderPojo);
		return orderPojo;
	}

	@Transactional(readOnly = true)
	public OrderPojo select(int orderId) {
		//select order by id
		TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
		query.setParameter("id", orderId);
		return getSingle(query);
	}

	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		//delete order by id
		Query query = entityManager.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(OrderPojo orderPojo){

	}

	@Transactional(readOnly = true)
	public List<OrderPojo> selectAll() {
		//retrieve all orders
		TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> selectBetweeenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		//select orders between two dates
		TypedQuery<OrderPojo> query = getQuery(select_all_orderDate, OrderPojo.class);
		return query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
	}

}
