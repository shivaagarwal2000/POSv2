package com.increff.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao orderItemDao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(ArrayList<OrderItemPojo> orderItemPojos) throws ApiException {

		for (OrderItemPojo orderItemPojo : orderItemPojos) {
			orderItemDao.insert(orderItemPojo);
		}
	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> getItemPojos(int orderId) {
		return orderItemDao.selectItems(orderId);
	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> getAll() {
		return orderItemDao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void deleteOrder(int orderId) {
		orderItemDao.deleteOrder(orderId);
	}

}
