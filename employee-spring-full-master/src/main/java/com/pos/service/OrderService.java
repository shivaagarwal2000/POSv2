package com.pos.service;

import com.pos.dao.OrderDao;
import com.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.pos.pojo.TableConstants.PENDING_STATUS;
import static com.pos.pojo.TableConstants.PLACED_STATUS;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackFor = ApiException.class)
    public OrderPojo add() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderPojo.setTime(zonedDateTime);
        orderPojo.setStatus(PENDING_STATUS);
        return orderDao.insert(orderPojo);
    }

    @Transactional(readOnly = true)
    public OrderPojo get(int orderId) throws ApiException {
        OrderPojo orderPojo = orderDao.select(orderId);
        if (Objects.isNull(orderPojo)) {
            throw new ApiException("Error: Order does not exist");
        }
        return orderPojo;
    }

    @Transactional(readOnly = true)
    public void placeOrder(int orderId) throws ApiException {
        OrderPojo orderPojo = get(orderId);
        orderPojo.setStatus(PLACED_STATUS);
        orderPojo.setTime(ZonedDateTime.now());
        orderDao.update(orderPojo);
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
