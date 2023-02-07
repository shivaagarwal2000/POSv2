package com.pos.service;

import com.pos.dao.OrderItemDao;
import com.pos.pojo.OrderItemPojo;
import org.commons.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(List<OrderItemPojo> orderItemPojos) throws ApiException {
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

    @Transactional(rollbackFor = ApiException.class)
    public void delete(int id) {
        orderItemDao.delete(id);
    }

    @Transactional(readOnly = true)
    public OrderItemPojo select(int orderItemId) throws ApiException { // TODO: logic : check all get, select to see if proper exception on no selection
        OrderItemPojo orderItemPojo = orderItemDao.select(orderItemId);
        if (Objects.isNull(orderItemPojo)) {
            throw new ApiException("Error: order item does not exists");
        }
        return orderItemDao.select(orderItemId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int orderItemId, OrderItemPojo orderItemPojo) throws ApiException {
        OrderItemPojo oldOrderItemPojo = select(orderItemId);
        oldOrderItemPojo.setProductId(orderItemPojo.getProductId());
        oldOrderItemPojo.setSellingprice(orderItemPojo.getSellingprice());
        oldOrderItemPojo.setQuantity(orderItemPojo.getQuantity());
        orderItemDao.update(oldOrderItemPojo);
    }
}
