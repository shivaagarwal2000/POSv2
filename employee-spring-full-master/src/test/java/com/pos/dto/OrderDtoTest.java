package com.pos.dto;

import com.pos.helper.posTestHelper;
//import com.pos.model.data.CommonOrderItemData;
import org.commons.CommonOrderItemData;
import com.pos.model.data.InventoryData;
import com.pos.model.data.OrderData;
import com.pos.model.data.SalesReportData;
import com.pos.model.forms.*;
import com.pos.service.AbstractUnitTest;
import org.commons.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.pos.pojo.TableConstants.PENDING_STATUS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto orderDto;

    @Autowired
    private BrandDto brandDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryDto inventoryDto;
    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        List<CommonOrderItemData> insertedOrderItemDataList = orderDto.getItemDatas(1);
        for (int i = 1; i <= 2; i++) {
            assertEquals(orderItemForms.get(i - 1).getBarcode(), insertedOrderItemDataList.get(i - 1).getBarcode());
            assertEquals(orderItemForms.get(i - 1).getQuantity(), insertedOrderItemDataList.get(i - 1).getQuantity());
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutBarcode() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm("", 20);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            fail("order dto adding order without barcode");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutQuantity() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), null);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            fail("order dto adding order without quantity");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: barcode/quantity can not be empty";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutProduct() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm("abc12", 20);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            fail("order dto adding order without product");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: product does not exists for barcode: abc12";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testAddWithoutSufficientInventory() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 2000);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(productForm2.getBarcode(), 20);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            fail("order dto adding order without sufficient inventory");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: not enough quantity to fulfil -name1";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testEditOrderItem() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderItemForm1.setQuantity(21);
        orderDto.editOrderItem(1, orderItemForm1);
        CommonOrderItemData commonOrderItemData = orderDto.getOrderItem(1);
        assertEquals(orderItemForm1.getQuantity(), commonOrderItemData.getQuantity());
        assertEquals(orderItemForm1.getBarcode(), commonOrderItemData.getBarcode());
    }

    @Test(expected = ApiException.class)
    public void testPlacedEditOrderItem() throws ApiException, IOException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            orderDto.placeOrder(1);
            orderItemForm1.setQuantity(21);
            orderDto.editOrderItem(1, orderItemForm1);
            fail("order dto editing a placed order");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: order is placed";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testPlaceOrder() throws ApiException, IOException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.placeOrder(1);
        try{
            orderDto.placeOrder(1);
            fail("order dto unable to place order");
        }catch (ApiException apiException) {
            final String ERROR_MSG = "Error: order is placed";
            assertEquals(ERROR_MSG, apiException.getMessage());
            InventoryData inventoryData1 = inventoryDto.get(1);
            InventoryData inventoryData2 = inventoryDto.get(2);
            Integer expectedRemainingQuantity = inventoryForm1.getQuantity() - orderItemForm1.getQuantity();
            assertEquals(expectedRemainingQuantity , inventoryData1.getQuantity());
            expectedRemainingQuantity = inventoryForm2.getQuantity() - orderItemForm2.getQuantity();
            assertEquals(expectedRemainingQuantity , inventoryData2.getQuantity());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testPlaceOrderWithoutInventory() throws ApiException, IOException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 1000);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 1000);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 1000);
        orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 1000);
        orderItemForms.clear();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.placeOrder(1);
        try {
            orderDto.placeOrder(2);
        }catch (ApiException apiException) {
            final String ERROR_MSG = "Error: not enough quantity to fulfil -name1";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testDeleteOrder() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 1000);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 1000);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.deleteOrder(1);
        List<OrderData> orderDataList = orderDto.getAll();
        List<CommonOrderItemData> commonOrderItemDataList = orderDto.getItemDatas(1);
        assertEquals(0, orderDataList.size());
        assertEquals(0, commonOrderItemDataList.size());
    }

    @Test(expected = ApiException.class)
    public void testDeletePlacedOrder() throws ApiException, IOException { // TODO: 1 remove IOException
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.placeOrder(1);
        try {
            orderDto.deleteOrder(1);
            fail("order dto deleting placed order");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: order is placed";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testDeleteOrderItem() throws ApiException {
        try {
            BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
            brandDto.add(brandForm);
            ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
            productDto.add(productForm1);
            ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
            productDto.add(productForm2);
            InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
            InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
            inventoryDto.add(inventoryForm1);
            inventoryDto.add(inventoryForm2);
            OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 1000);
            OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 1000);
            List<OrderItemForm> orderItemForms = new ArrayList<>();
            orderItemForms.add(orderItemForm1);
            orderItemForms.add(orderItemForm2);
            orderDto.addOrder(orderItemForms);
            orderDto.deleteOrderItem(1);
            orderDto.getOrderItem(1);
            fail("order dto unable to delete order item");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: order item does not exists";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test(expected = ApiException.class)
    public void testDeletePlacedOrderItem() throws ApiException, IOException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.placeOrder(1);
        try {
            orderDto.deleteOrderItem(1);
            fail("order dto deleting placed order item");
        }
        catch (ApiException apiException) {
            final String ERROR_MSG = "Error: order is placed";
            assertEquals(ERROR_MSG, apiException.getMessage());
            throw apiException;
        }
    }

    @Test
    public void testGetAllOrderItems() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        List<CommonOrderItemData> insertedOrderItemDataList = orderDto.getItemDatas(1);
        for (int i = 1; i <= 2; i++) {
            assertEquals(orderItemForms.get(i - 1).getBarcode(), insertedOrderItemDataList.get(i - 1).getBarcode());
            assertEquals(orderItemForms.get(i - 1).getQuantity(), insertedOrderItemDataList.get(i - 1).getQuantity());
        }
    }

    @Test
    public void testGetAllOrders() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        List<OrderData> orderDataList = orderDto.getAll();
        assertEquals(1, orderDataList.size());
        assertEquals(PENDING_STATUS, orderDataList.get(0).getStatus());
    }

    @Test
    public void testSalesReport() throws ApiException, IOException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        orderDto.placeOrder(1);
        SalesReportForm salesReportForm = new SalesReportForm();
        salesReportForm.setBrand("");
        salesReportForm.setCategory("");
        salesReportForm.setEndDate("");
        salesReportForm.setStartDate("");
        List<SalesReportData> salesReportDataList = orderDto.getSalesReportDatas(salesReportForm);
        int totalSoldQuantity = orderItemForm1.getQuantity() + orderItemForm2.getQuantity();
        assertEquals(1, salesReportDataList.size());
        assertEquals(totalSoldQuantity, salesReportDataList.get(0).getQuantity());
    }

    @Test
    public void testGetOrderItem() throws ApiException {
        BrandForm brandForm = posTestHelper.createBrandForm("brand", "category");
        brandDto.add(brandForm);
        ProductForm productForm1 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name1", "barcode1", 5.00);
        productDto.add(productForm1);
        ProductForm productForm2 = posTestHelper.createProductForm(brandForm.getBrand(), brandForm.getCategory(), "name2", "barcode2", 10.00);
        productDto.add(productForm2);
        InventoryForm inventoryForm1 = posTestHelper.createInventoryForm(productForm1.getBarcode(), 1000);
        InventoryForm inventoryForm2 = posTestHelper.createInventoryForm(productForm2.getBarcode(), 1000);
        inventoryDto.add(inventoryForm1);
        inventoryDto.add(inventoryForm2);
        OrderItemForm orderItemForm1 = posTestHelper.createOrderItemForm(inventoryForm1.getBarcode(), 20);
        OrderItemForm orderItemForm2 = posTestHelper.createOrderItemForm(inventoryForm2.getBarcode(), 20);
        List<OrderItemForm> orderItemForms = new ArrayList<>();
        orderItemForms.add(orderItemForm1);
        orderItemForms.add(orderItemForm2);
        orderDto.addOrder(orderItemForms);
        CommonOrderItemData commonOrderItemData = orderDto.getOrderItem(1);
        assertEquals(orderItemForm1.getQuantity(), commonOrderItemData.getQuantity());
        assertEquals(orderItemForm1.getBarcode(), commonOrderItemData.getBarcode());
    }
}
