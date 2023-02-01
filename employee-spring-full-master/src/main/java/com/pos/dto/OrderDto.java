package com.pos.dto;

import com.pos.client.InvoiceClient;
import com.pos.helper.OrderDtoHelper;
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
import org.springframework.transaction.annotation.Isolation;
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
            OrderData orderData = OrderDtoHelper.convert(orderPojo);
            orderDatas.add(orderData);
        }
        return orderDatas;
    }

    @Transactional(readOnly = true)
    public CommonOrderItemData getOrderItem(int itemId) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemService.select(itemId);
        ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
        CommonOrderItemData orderItemData = OrderDtoHelper.convert(productPojo, orderItemPojo);
        return orderItemData;
    }

    //TODO: logic - edge - if multiple entries together for item -- ?
    @Transactional(rollbackFor = ApiException.class)
    public int addOrder(List<OrderItemForm> forms) throws ApiException {
        // add order - create order entry, order item entries with order still in pending state
        validateListOrderForm(forms);
        OrderPojo orderPojo = orderService.add();
        orderItemService.add(convert(orderPojo.getId(), forms));
        return orderPojo.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    // TODO Transactional not required as there is single save call Priority: 5
    public void editOrderItem(int orderItemId, OrderItemForm orderItemForm) throws ApiException {
        ProductPojo productPojo = validateOrderForm(orderItemForm);
        OrderItemPojo oldOrderItemPojo = orderItemService.select(orderItemId);
        OrderPojo orderPojo = orderService.get(oldOrderItemPojo.getOrderId());
        OrderDtoHelper.isPlaced(orderPojo);
//        ProductPojo productPojo = productService.get(orderItemForm.getBarcode()); // TODO: check if validate can return productPojo
        OrderItemPojo orderItemPojo = OrderDtoHelper.convert(orderItemForm, productPojo, orderPojo.getId());
        orderItemService.update(orderItemId, orderItemPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void placeOrder(int orderId) throws ApiException, IOException {
        OrderPojo orderPojo = orderService.get(orderId);
        OrderDtoHelper.isPlaced(orderPojo);
        List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderId);
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            validateInventory(orderItemPojo.getQuantity(), productService.get(orderItemPojo.getProductId()));
        }
        reduceInventory(orderItemPojos);
        orderService.placeOrder(orderId);
        getInvoice(orderId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteOrder(int orderId) throws ApiException {
        //delete order and its items, given orderId, if not placed
        OrderPojo orderPojo = orderService.get(orderId);
        OrderDtoHelper.isPlaced(orderPojo);
        orderItemService.deleteOrder(orderId);
        orderService.delete(orderId);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void deleteOrderItem(int id) throws ApiException {
        //delete order item, given order item id, if order not placed
        OrderItemPojo orderItemPojo = orderItemService.select(id);
        OrderPojo orderPojo = orderService.get(orderItemPojo.getOrderId());
        OrderDtoHelper.isPlaced(orderPojo);
        orderItemService.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CommonOrderItemData> getItemDatas(int orderId) throws ApiException {
        //retrieve order items for an order
        List<OrderItemPojo> itemPojos = orderItemService.getItemPojos(orderId);
        List<CommonOrderItemData> itemDatas = new ArrayList<CommonOrderItemData>();
        for (OrderItemPojo orderItemPojo : itemPojos) {
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            CommonOrderItemData orderItemData = OrderDtoHelper.convert(productPojo, orderItemPojo);
            itemDatas.add(orderItemData);
        }
        return itemDatas;
    }

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

        if (startDate.isAfter(endDate)) {
            throw new ApiException("Error: start date can't be after end date");
        }

        String requestedBrand = salesReportForm.getBrand();
        String requestedCategory = salesReportForm.getCategory();

        List<OrderPojo> orderPojos = orderService.getBetweenDates(startDate, endDate);
        List<SalesReportData> salesReportDatas = new ArrayList<SalesReportData>();
        HashMap<String, SalesReportData> salesReportMap = new HashMap<String, SalesReportData>();

        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
                if ((requestedBrand.equals(brandPojo.getBrand()) && requestedCategory.equals(brandPojo.getCategory())) ||
                        (requestedBrand.equals(brandPojo.getBrand()) && StringUtil.isEmpty(requestedCategory)) ||
                        (StringUtil.isEmpty(requestedBrand) && requestedCategory.equals(brandPojo.getCategory())) ||
                        (StringUtil.isEmpty(requestedBrand) && StringUtil.isEmpty(requestedCategory))) {
                    if (salesReportMap.containsKey(brandPojo.getBrand() + "+" + brandPojo.getCategory())) {
                        // TODO Move below code to helper Priority: 5
                        SalesReportData salesReportData = new SalesReportData();
                        salesReportData.setBrand(brandPojo.getBrand());
                        salesReportData.setCategory(brandPojo.getCategory());
                        salesReportData.setQuantity(salesReportMap.get(brandPojo.getBrand() + "+" + brandPojo.getCategory()).getQuantity()
                                + orderItemPojo.getQuantity());
                        salesReportData.setRevenue(salesReportMap.get(brandPojo.getBrand() + "+" + brandPojo.getCategory()).getRevenue()
                                + orderItemPojo.getSellingprice());
                        salesReportMap.put(brandPojo.getBrand() + "+" + brandPojo.getCategory(), salesReportData);
                    } else {
                        SalesReportData salesReportData = new SalesReportData();
                        salesReportData.setBrand(brandPojo.getBrand());
                        salesReportData.setCategory(brandPojo.getCategory());
                        salesReportData.setQuantity(orderItemPojo.getQuantity());
                        salesReportData.setRevenue(orderItemPojo.getSellingprice());
                        salesReportMap.put(brandPojo.getBrand() + "+" + brandPojo.getCategory(), salesReportData);
                    }
                }
            }
        }

        for (String key : salesReportMap.keySet()) {
            salesReportDatas.add(salesReportMap.get(key));
        }
        return salesReportDatas;
    }

    public String getInvoice(int orderId) throws ApiException, IOException {// TODO Throw only apiException Priority: 5
        List<CommonOrderItemData> commonOrderItemDatas = getItemDatas(orderId);
        return invoiceClient.getInvoice(commonOrderItemDatas);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void reduceInventory(List<OrderItemPojo> orderItemPojos) throws ApiException {
        //reduce inventory for order items
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemPojo.getQuantity());
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        }
    }

    private List<OrderItemPojo> convert(int orderId, List<OrderItemForm> forms) throws ApiException {
        //convert list of order item form to list of order item pojo
        List<OrderItemPojo> orderItemPojos = new ArrayList<OrderItemPojo>();
        for (OrderItemForm form : forms) {
            ProductPojo productPojo = productService.getCheck(form.getBarcode());
            OrderItemPojo orderItemPojo = OrderDtoHelper.convert(form, productPojo, orderId);
            orderItemPojos.add(orderItemPojo);
        }
        return orderItemPojos;
    }

    private ProductPojo validateOrderForm(OrderItemForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBarcode()) || Objects.isNull(form.getQuantity()) || form.getQuantity() == 0) {
            throw new ApiException("Error: barcode/quantity can not be empty");
        }
        form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
        ProductPojo productPojo = productService.getCheck(form.getBarcode());
        validateInventory(form.getQuantity(), productPojo);
        return productPojo;
    }

    private void validateInventory(int orderItemQuantity, ProductPojo productPojo) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(productPojo.getId());
        if (inventoryPojo.getQuantity() < orderItemQuantity) {
            throw new ApiException("Error: not enough quantity to fulfil -" + productPojo.getName());
        }
    }

    private void validateListOrderForm(List<OrderItemForm> forms) throws ApiException {
        HashMap<String, Boolean> productCheckMap = new HashMap<String, Boolean>();
        for (OrderItemForm form : forms) {
            ProductPojo productPojo = validateOrderForm(form);
            if (productCheckMap.containsKey(productPojo.getBarcode())) {
                throw new ApiException("Error: repeated entry");
            }
            else {
                productCheckMap.put(productPojo.getBarcode(), true);
            }
        }
    }
}
