package com.pos.dto;

import com.pos.client.InvoiceClient;
import com.pos.model.data.CommonOrderItemData;
import com.pos.model.data.OrderData;
import com.pos.model.data.SalesReportData;
import com.pos.model.forms.OrderItemForm;
import com.pos.model.forms.SalesReportForm;
import com.pos.pojo.*;
import com.pos.service.*;
import com.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.pos.pojo.TableConstants.PENDING_STATUS;
import static com.pos.pojo.TableConstants.PLACED_STATUS;

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
    @Autowired
    private InvoiceClient invoiceClient;

    public List<OrderData> getAll() {
        // Get list of all orders
        List<OrderPojo> orderPojos = orderService.getAll();
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            OrderData orderData = new OrderData();
            orderData.setId(orderPojo.getId());
//            orderData.setOrderTime(orderPojo.getTime().toString());
            orderData.setOrderTime(orderPojo.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss z")));
            orderData.setStatus(orderPojo.getStatus());
            orderDatas.add(orderData);
        }
        return orderDatas;
    }

    @Transactional(readOnly = true)
    public CommonOrderItemData getOrderItem(int itemId) throws ApiException {
        CommonOrderItemData orderItemData = new CommonOrderItemData();
        OrderItemPojo orderItemPojo = orderItemService.select(itemId);
        ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setMrp(productPojo.getMrp());
        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setProductName(productPojo.getName());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingprice());
        return orderItemData;
    }

    //TODO: edge - if multiple entries together for item -- ?
    @Transactional(rollbackFor = ApiException.class)
    public void addOrder(List<OrderItemForm> forms) throws ApiException {
//      add the order
        for (OrderItemForm form : forms) {// TODO Move form validation to helper Priority: 5
            if (StringUtil.isEmpty(form.getBarcode()) || StringUtil.isEmpty(String.valueOf(form.getQuantity()))) {
                throw new ApiException("Error: barcode/quantity can not be empty");
            }
        }
//		get the length of order table to get order id
        int orderLen = 0;// TODO You can first persist order which will set the orderId then use that in items Priority: 5
        ArrayList<OrderItemPojo> orderItemPojos = new ArrayList<OrderItemPojo>();
        for (OrderItemForm form : forms) {
            form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
            ProductPojo productPojo = productService.get(form.getBarcode());// TODO Use getcheck Priority: 5
            if (Objects.isNull(productPojo)) {
                throw new ApiException("Error: given barcode does not exist for -" + form.getBarcode());
            }
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            if (inventoryPojo.getQuantity() < form.getQuantity()) {
                throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
            }
            OrderItemPojo orderItemPojo = new OrderItemPojo();// TODO Move this to helper Priority: 5
            orderItemPojo.setOrderId(orderLen);
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setQuantity(form.getQuantity());
            orderItemPojo.setSellingprice(form.getQuantity() * productPojo.getMrp());
            orderItemPojos.add(orderItemPojo);
        }

//		TODO: date - zone datetime change
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();// TODO Move order creation before orderItems Priority: 5
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

    @Transactional(rollbackFor = ApiException.class)// TODO Transactional not required as there is single save call Priority: 5
    public void editOrderItem(int orderItemId, OrderItemForm orderItemForm) throws ApiException {

        // TODO Move form validation to helper Priority: 5
        if (StringUtil.isEmpty(orderItemForm.getBarcode()) || StringUtil.isEmpty(String.valueOf(orderItemForm.getQuantity()))) {
            throw new ApiException("Error: barcode/quantity can not be empty");
        }
        orderItemForm.setBarcode(StringUtil.toLowerCase(orderItemForm.getBarcode()));
        ProductPojo productPojo = productService.get(orderItemForm.getBarcode());// TODO use getCheck Priority: 5
        if (Objects.isNull(productPojo)) {
            throw new ApiException("Error: given barcode does not exist for -" + orderItemForm.getBarcode());
        }
        InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
        if (inventoryPojo.getQuantity() < orderItemForm.getQuantity()) {
            throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
        }
        OrderItemPojo oldOrderItemPojo = orderItemService.select(orderItemId);
        OrderPojo orderPojo = orderService.get(oldOrderItemPojo.getOrderId());
        if (orderPojo.getStatus() == PLACED_STATUS) {// TODO Use Objects.isEqual Priority: 5
//            trying to edit placed orderItem
            return;// TODO Throw ApiException Priority: 5
        }
        OrderItemPojo orderItemPojo = new OrderItemPojo();// TODO Move to helper Priority: 5
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
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());// TODO User getCheck Priority: 5
            if (Objects.isNull(productPojo)) {
                throw new ApiException("Error: one of the product is no more");
            }
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());// TODO This is being used too many times, move to a seprate function in OrderDto Priority: 5
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
    public void deleteOrder(int orderId) {// TODO Check if order is placed Priority: 5
        orderItemService.deleteOrder(orderId);
        orderService.delete(orderId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteOrderItem(int id) {// TODO Check if order is placed Priority: 5
        orderItemService.delete(id);
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

    public List<CommonOrderItemData> getItemDatas(int orderId) throws ApiException {// TODO Refactor and move code to helper Priority: 5
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

    //TODO: change to get all filters.
    public List<SalesReportData> getSalesReportDatas(SalesReportForm salesReportForm) throws ApiException {
        if (salesReportForm.getStartDate().isEmpty()) {// TODO Move this to normalize helper Priority: 5
            salesReportForm.setStartDate("1970-01-01");
        }
        if (salesReportForm.getEndDate().isEmpty()) {
            salesReportForm.setEndDate("3000-01-01");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ZonedDateTime startDate = LocalDate.parse(salesReportForm.getStartDate(), dtf).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endDate = LocalDate.parse(salesReportForm.getEndDate(), dtf).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);
//        System.out.println(endDate);
        if (startDate.isAfter(endDate)){
            throw new ApiException("Error: start date can't be after end date");
        }
        String brand = salesReportForm.getBrand();// TODO Unused variables Priority: 5
        String category = salesReportForm.getCategory();

        List<OrderPojo> orderPojos = orderService.getBetweenDates(startDate, endDate);
//        System.out.println(orderPojos);
        List<SalesReportData> salesReportDatas = new ArrayList<SalesReportData>();
        HashMap<String, SalesReportData> hash_map = new HashMap<String, SalesReportData>();// TODO Rename variable  Priority: 5

        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderPojo.getId());

            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
                // TODO Apply filter based on brand and category Priority: 5
                if (hash_map.containsKey(brandPojo.getBrand() + brandPojo.getCategory())) {
                    // TODO Move below code to helper Priority: 5
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

    public String getInvoice(int orderId) throws ApiException, IOException {// TODO Throw only apiException Priority: 5
        List<CommonOrderItemData> commonOrderItemDatas = getItemDatas(orderId);
        return invoiceClient.getInvoice(commonOrderItemDatas);
    }
}
