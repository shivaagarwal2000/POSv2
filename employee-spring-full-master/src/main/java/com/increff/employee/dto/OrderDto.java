package com.increff.employee.dto;

import com.increff.employee.model.data.CommonOrderItemData;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.forms.OrderItemForm;
import com.increff.employee.model.forms.SalesReportForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.*;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.increff.employee.pojo.TableConstants.PENDING_STATUS;
import static com.increff.employee.pojo.TableConstants.PLACED_STATUS;

@Service
public class OrderDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BrandService brandService;

    public List<OrderData> getAll() {
        // Get list of all orders
        List<OrderPojo> orderPojos = orderService.getAll();
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            OrderData orderData = new OrderData();
            orderData.setId(orderPojo.getId());
            orderData.setOrderTime(orderPojo.getTime().toString());
            orderData.setStatus(orderPojo.getStatus());
            orderDatas.add(orderData);
        }
        return orderDatas;
    }

    //TODO: edge - if multiple entries together for item -- ?
    @Transactional(rollbackFor = ApiException.class)
    public void addOrder(List<OrderItemForm> forms) throws ApiException {
//      add the order
        for (OrderItemForm form : forms) {
            if (StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(String.valueOf(form.getQuantity()))) {
                throw new ApiException("Error: barcode/quantity can not be empty");
            }
        }
//		get the length of order table to get order id
        int orderLen = 0;
        ArrayList<OrderItemPojo> orderItemPojos = new ArrayList<OrderItemPojo>();
        for (OrderItemForm form : forms) {
            form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
            ProductPojo productPojo = productService.get(form.getBarcode());
            if (Objects.isNull(productPojo)) {
                throw new ApiException("Error: given barcode does not exist for -" + form.getBarcode());
            }
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            if (inventoryPojo.getQuantity() < form.getQuantity()) {
                throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
            }
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(orderLen);
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setQuantity(form.getQuantity());
            orderItemPojo.setSellingprice(form.getQuantity() * productPojo.getMrp());
            orderItemPojos.add(orderItemPojo);
        }

//		TODO: date - zone datetime change
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        String orderTime = zonedDateTime.toString();
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(orderTime);
        orderPojo.setTime(zonedDateTime);
        orderPojo.setStatus(PENDING_STATUS);

        orderService.add(orderPojo);
        OrderPojo orderPojo2 = orderService.get(zonedDateTime);
//        OrderPojo orderPojo2 = orderService.add(orderPojo);

        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemPojo.setOrderId(orderPojo2.getId());
        }
//            InventoryPojo inventoryPojo = inventoryService.get(orderItemPojo.getProductId());
//            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemPojo.getQuantity());
//            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        orderItemService.add(orderItemPojos);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void editOrderItem(int orderItemId, OrderItemForm orderItemForm) throws ApiException {

        if (StringUtil.isEmpty(orderItemForm.getBarcode()) || StringUtil.isEmpty(String.valueOf(orderItemForm.getQuantity()))) {
            throw new ApiException("Error: barcode/quantity can not be empty");
        }
        orderItemForm.setBarcode(StringUtil.toLowerCase(orderItemForm.getBarcode()));
        ProductPojo productPojo = productService.get(orderItemForm.getBarcode());
        if (Objects.isNull(productPojo)) {
            throw new ApiException("Error: given barcode does not exist for -" + orderItemForm.getBarcode());
        }
        InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
        if (inventoryPojo.getQuantity() < orderItemForm.getQuantity()) {
            throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
        }
        OrderItemPojo oldOrderItemPojo = orderItemService.select(orderItemId);
        OrderPojo orderPojo = orderService.get(oldOrderItemPojo.getOrderId());
        if (orderPojo.getStatus() == PLACED_STATUS) {
//            trying to edit placed orderItem
            return;
        }
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(oldOrderItemPojo.getOrderId());
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setSellingprice(orderItemForm.getQuantity() * productPojo.getMrp());
        orderItemService.update(orderItemId, orderItemPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void placeOrder(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderId);
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            if (Objects.isNull(productPojo)) {
                throw new ApiException("Error: one of the product is no more");
            }
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            if (inventoryPojo.getQuantity() < orderItemPojo.getQuantity()) {
                throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
            }
        }
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemPojo.getQuantity());
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        }
        orderService.placeOrder(orderId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteOrder(int orderId) {
        orderItemService.deleteOrder(orderId);
        orderService.delete(orderId);
    }

    //TODO: no use -- delete
    public List<CommonOrderItemData> getAllOrderItems() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<CommonOrderItemData> orderItemDatas = new ArrayList<CommonOrderItemData>();
        for (OrderItemPojo orderItemPojo : list) {
            CommonOrderItemData orderItemData = new CommonOrderItemData();
            orderItemData.setId(orderItemPojo.getId());
            orderItemData.setOrderId(orderItemPojo.getOrderId());
            orderItemData.setQuantity(orderItemPojo.getQuantity());
            orderItemData.setSellingPrice(orderItemPojo.getSellingprice());

            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            orderItemData.setBarcode(productPojo.getBarcode());
            orderItemData.setProductName(productPojo.getName());
            orderItemData.setMrp(productPojo.getMrp());

            orderItemDatas.add(orderItemData);
        }

        return orderItemDatas;

    }

    public List<CommonOrderItemData> getItemDatas(int orderId) throws ApiException {
        List<OrderItemPojo> itemPojos = orderItemService.getItemPojos(orderId);
        List<CommonOrderItemData> itemDatas = new ArrayList<CommonOrderItemData>();
        for (OrderItemPojo orderItemPojo : itemPojos) {
            CommonOrderItemData orderItemData = new CommonOrderItemData();

            orderItemData.setId(orderItemPojo.getId());
            orderItemData.setOrderId(orderItemPojo.getOrderId());
            ProductPojo pojo = productService.get(orderItemPojo.getProductId());
            orderItemData.setBarcode(pojo.getBarcode());
            orderItemData.setProductName(pojo.getName());
            orderItemData.setMrp(pojo.getMrp());
            orderItemData.setQuantity(orderItemPojo.getQuantity());
            orderItemData.setSellingPrice(orderItemPojo.getSellingprice());

            itemDatas.add(orderItemData);
        }
        return itemDatas;
    }

    //TODO: change to only fetch with start date, end date
    public List<SalesReportData> getSalesReportDatas(SalesReportForm salesReportForm) throws ApiException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ZonedDateTime startDate = LocalDate.parse(salesReportForm.getStartDate(), dtf).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endDate = LocalDate.parse(salesReportForm.getEndDate(), dtf).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);
        System.out.println(endDate);
        String brand = salesReportForm.getBrand();
        String category = salesReportForm.getCategory();

//        if (startDate.isEmpty()) {
//            startDate = "1970";
//        }
//        if (endDate.isEmpty()) {
//            endDate = "3000";
//        }
        List<OrderPojo> orderPojos = orderService.getBetweenDates(startDate, endDate);
        System.out.println(orderPojos);
        List<SalesReportData> salesReportDatas = new ArrayList<SalesReportData>();
        HashMap<String, SalesReportData> hash_map = new HashMap<String, SalesReportData>();

        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderPojo.getId());

            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
                if (hash_map.containsKey(brandPojo.getBrand() + brandPojo.getCategory())) {
                    SalesReportData salesReportData = new SalesReportData();
                    salesReportData.setBrand(brandPojo.getBrand());
                    salesReportData.setCategory(brandPojo.getCategory());
                    salesReportData
                            .setQuantity(hash_map.get(brandPojo.getBrand() + brandPojo.getCategory()).getQuantity()
                                    + orderItemPojo.getQuantity());
                    salesReportData.setRevenue(hash_map.get(brandPojo.getBrand() + brandPojo.getCategory()).getRevenue()
                            + orderItemPojo.getSellingprice());
                    hash_map.put(brandPojo.getBrand() + brandPojo.getCategory(), salesReportData);
                } else {
                    SalesReportData salesReportData = new SalesReportData();
                    salesReportData.setBrand(brandPojo.getBrand());
                    salesReportData.setCategory(brandPojo.getCategory());
                    salesReportData.setQuantity(orderItemPojo.getQuantity());
                    salesReportData.setRevenue(orderItemPojo.getSellingprice());
                    hash_map.put(brandPojo.getBrand() + brandPojo.getCategory(), salesReportData);
                }
            }

        }

        for (String key : hash_map.keySet()) {
            salesReportDatas.add(hash_map.get(key));
        }

        return salesReportDatas;

    }

}
