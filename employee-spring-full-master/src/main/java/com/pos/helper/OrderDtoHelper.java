package com.pos.helper;

//import com.pos.model.data.CommonOrderItemData;
import org.commons.CommonOrderItemData;
import com.pos.model.data.OrderData;
import com.pos.model.forms.OrderItemForm;
import com.pos.pojo.OrderItemPojo;
import com.pos.pojo.OrderPojo;
import com.pos.pojo.ProductPojo;
import org.commons.util.ApiException;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.pos.pojo.TableConstants.PLACED_STATUS;

public class OrderDtoHelper {

    public static OrderData convert(OrderPojo orderPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setOrderTime(orderPojo.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss z")));
        orderData.setStatus(orderPojo.getStatus());
        return orderData;
    }

    public static CommonOrderItemData convert(ProductPojo productPojo, OrderItemPojo orderItemPojo) {
        CommonOrderItemData orderItemData = new CommonOrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setMrp(productPojo.getMrp());
        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setProductName(productPojo.getName());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingprice());
        return orderItemData;
    }

    public static void isPlaced(OrderPojo orderPojo) throws ApiException {
        if (Objects.equals(orderPojo.getStatus(), PLACED_STATUS)) {
            throw new ApiException("Error: order is placed");
        }
    }

    public static OrderItemPojo convert(OrderItemForm form, ProductPojo productPojo, int orderId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(form.getQuantity());
        orderItemPojo.setSellingprice(form.getQuantity() * productPojo.getMrp());
        return orderItemPojo;
    }
}
