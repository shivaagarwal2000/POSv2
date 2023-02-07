package com.pos.dto;

import com.pos.model.data.DaySalesData;
import com.pos.pojo.DaySalesPojo;
import com.pos.pojo.OrderItemPojo;
import com.pos.pojo.OrderPojo;
import org.commons.util.ApiException;
import com.pos.service.DaySalesService;
import com.pos.service.OrderItemService;
import com.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaySalesDto {

    @Autowired
    private DaySalesService daySalesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    public void add() throws ApiException {
        ZonedDateTime prevDateStart = getPrevStartEnd(true);
        ZonedDateTime prevDateEnd = getPrevStartEnd(false);
        List<OrderPojo> orderPojos = orderService.getBetweenDates(prevDateStart, prevDateEnd);
        if (orderPojos.size() == 0) {
            return;
        }
        int prevDayOrderCount = 0;
        int prevDayOrderItemsCount = 0;
        double totalRevenue = 0;
        for (OrderPojo orderPojo : orderPojos) {
            prevDayOrderCount += 1;
            List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                prevDayOrderItemsCount += orderItemPojo.getQuantity();
                totalRevenue += orderItemPojo.getSellingprice();
            }
        }
        DaySalesPojo daySalesPojo = convert(prevDateStart, prevDayOrderCount, prevDayOrderItemsCount, totalRevenue);
        daySalesService.add(daySalesPojo);
    }

    public List<DaySalesData> getAll() {
        List<DaySalesPojo> daySalesPojoList = daySalesService.getAll();
        List<DaySalesData> daySalesDataList = new ArrayList<>();
        for (DaySalesPojo daySalesPojo: daySalesPojoList) {
            daySalesDataList.add(convert(daySalesPojo));
        }
        return daySalesDataList;
    }

    private ZonedDateTime getPrevStartEnd(boolean flag) {
        if (flag) {
            return LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault());
        }
        else {
            return LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);
        }
    }

    private DaySalesData convert(DaySalesPojo daySalesPojo) {
        DaySalesData daySalesData = new DaySalesData();
        daySalesData.setDate(daySalesPojo.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        daySalesData.setOrderCount(daySalesPojo.getInvoiced_orders_count());
        daySalesData.setOrderItemCount(daySalesPojo.getInvoiced_items_count());
        daySalesData.setTotalRevenue(daySalesPojo.getTotal_revenue());
        return daySalesData;
    }
    private DaySalesPojo convert(ZonedDateTime entryDate, int dayOrderCount, int dayOrderItemCount, double revenue) {
        DaySalesPojo daySalesPojo = new DaySalesPojo();
        daySalesPojo.setDate(entryDate);
        daySalesPojo.setInvoiced_orders_count(dayOrderCount);
        daySalesPojo.setInvoiced_items_count(dayOrderItemCount);
        daySalesPojo.setTotal_revenue(revenue);
        return daySalesPojo;
    }

}
