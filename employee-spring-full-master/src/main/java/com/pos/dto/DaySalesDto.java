package com.pos.dto;

import com.pos.pojo.DaySalesPojo;
import com.pos.pojo.OrderItemPojo;
import com.pos.pojo.OrderPojo;
import com.pos.service.ApiException;
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
import java.util.List;

@EnableScheduling
@Service
public class DaySalesDto {

    @Autowired
    private DaySalesService daySalesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    //TODO: move it to the properties file
    //TODO: run for each second with many orders
    @Scheduled(cron = "0 0 8 * * *")
    public void add() throws ApiException {
        //TODO: date - get previous date
//		String prevdate = "";
//		List<OrderPojo> orderPojos = orderService.selectByDate(prevdate);

        ZonedDateTime prevDateStart = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime prevDateEnd = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);
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
                prevDayOrderItemsCount += 1;
                totalRevenue += orderItemPojo.getSellingprice();
            }
        }
        DaySalesPojo daySalesPojo = new DaySalesPojo();
        daySalesPojo.setDate(prevDateStart);
        daySalesPojo.setInvoiced_orders_count(prevDayOrderCount);
        daySalesPojo.setInvoiced_items_count(prevDayOrderItemsCount);
        daySalesPojo.setTotal_revenue(totalRevenue);
        daySalesService.add(daySalesPojo);
    }

}
