package com.increff.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(OrderPojo orderPojo) throws ApiException {
		orderDao.insert(orderPojo);
	}

	@Transactional(readOnly = true)
	public OrderPojo get(String orderTime) {
		return orderDao.select(orderTime);

	}

	@Transactional(readOnly = true)
	public List<OrderPojo> getAll() {
		return orderDao.selectAll();
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> getBetweenDates(String startDate, String endDate) {
		return orderDao.selectBetweeenDates(startDate, endDate);
	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) {
		orderDao.delete(id);
	}

}
