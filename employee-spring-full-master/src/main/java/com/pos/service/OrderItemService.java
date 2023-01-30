package com.pos.service;

import com.pos.dao.OrderItemDao;
import com.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

	@Transactional(readOnly = true)
    public OrderItemPojo select(int orderItemId) {
        return orderItemDao.select(orderItemId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int orderItemId, OrderItemPojo orderItemPojo){
        OrderItemPojo oldOrderItemPojo = orderItemDao.select(orderItemId);
        oldOrderItemPojo.setProductId(orderItemPojo.getProductId());
        oldOrderItemPojo.setSellingprice(orderItemPojo.getSellingprice());
        oldOrderItemPojo.setQuantity(orderItemPojo.getQuantity());
        orderItemDao.update(oldOrderItemPojo);
    }
}
