package com.increff.employee.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.CommonOrderItemData;
import com.increff.employee.model.forms.OrderItemForm;
import com.increff.employee.model.forms.SalesReportForm;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;

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
        List<OrderPojo> orderPojos = orderService.getAll();
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            OrderData orderData = new OrderData();
            orderData.setId(orderPojo.getId());
            orderData.setOrderTime(orderPojo.getOrderTime());
            orderDatas.add(orderData);
        }
        return orderDatas;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void add(List<OrderItemForm> forms) throws ApiException {
//		for each form, check empty, normalise, validate values
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
            if (productPojo == null) {
                throw new ApiException("Error: given barcode does not exist");
            }
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            if (inventoryPojo.getQuantity() < form.getQuantity()) {
                throw new ApiException("Error: not enough quantity to fulfil");
            }
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(orderLen);
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setQuantity(form.getQuantity());
            orderItemPojo.setSellingprice(form.getQuantity() * productPojo.getMrp());
            orderItemPojos.add(orderItemPojo);
        }

//		generate entry into the orderMasterTable for this order
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String orderTime = dateFormat.format(date);
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderTime(orderTime);
        orderService.add(orderPojo);
        OrderPojo orderPojo2 = orderService.get(orderTime);

        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemPojo.setOrderId(orderPojo2.getId());
            InventoryPojo inventoryPojo = inventoryService.get(orderItemPojo.getProductId());
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemPojo.getQuantity());
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        }

        orderItemService.add(orderItemPojos);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteOrder(int orderId) {
        orderItemService.deleteOrder(orderId);
        orderService.delete(orderId);
    }

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

    public List<SalesReportData> getSalesReportDatas(SalesReportForm salesReportForm) throws ApiException {
        String startDate = salesReportForm.getStartDate();
        String endDate = salesReportForm.getEndDate();
        String brand = salesReportForm.getBrand();
        String category = salesReportForm.getCategory();

        if (startDate.isEmpty()) {
            startDate = "1970";
        }
        if (endDate.isEmpty()) {
            endDate = "3000";
        }
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
