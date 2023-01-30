package com.pos.service;

import com.pos.dao.OrderDao;
import com.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static com.pos.pojo.TableConstants.PLACED_STATUS;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackFor = ApiException.class)
    public OrderPojo add(OrderPojo orderPojo) throws ApiException {
        return orderDao.insert(orderPojo);
    }

    @Transactional(readOnly = true)
    public OrderPojo get(ZonedDateTime orderTime) {
        return orderDao.select(orderTime);

    }

    @Transactional(readOnly = true)
    public OrderPojo get(int orderId) {
        return orderDao.select(orderId);

    }

    @Transactional(readOnly = true)
    public void placeOrder(int orderId) {
//      change status of order "pending" -> "placed"
        OrderPojo orderPojo = get(orderId);
        orderPojo.setStatus(PLACED_STATUS);
//		TODO: date - put current time
        orderPojo.setTime(ZonedDateTime.now());
        orderDao.update(orderPojo);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> selectByDate(String orderDate) {
        return orderDao.selectByDate(orderDate);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> getAll() {
        return orderDao.selectAll();
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> getBetweenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        return orderDao.selectBetweeenDates(startDate, endDate);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void delete(int id) {
        orderDao.delete(id);
    }

}
