package com.increff.employee.dto;

import com.increff.employee.pojo.DaySalesPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaySalesDto {

	@Autowired
	private DaySalesService daySalesService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;

	//TODO: move it to the properties file
	@Scheduled(cron = "0 0 8 * * *")
	public void add() throws ApiException {
		//TODO: get previous date
		String prevdate = "";

		List<OrderPojo> orderPojos = orderService.selectByDate(prevdate);
		if (orderPojos.size() == 0){//TODO: empty function
			return;
		}
		int prevDayOrderCount = 0;
		int prevDayOrderItemsCount = 0;
		double totalRevenue = 0;
		for (OrderPojo orderPojo: orderPojos){
			prevDayOrderCount += 1;
			List<OrderItemPojo> orderItemPojos = orderItemService.getItemPojos(orderPojo.getId());
			for(OrderItemPojo orderItemPojo: orderItemPojos){
				prevDayOrderItemsCount += 1;
				totalRevenue += orderItemPojo.getSellingprice();
			}
		}
		DaySalesPojo daySalesPojo = new DaySalesPojo();
		daySalesPojo.setDate(prevdate);
		daySalesPojo.setInvoiced_orders_count(prevDayOrderCount);
		daySalesPojo.setInvoiced_items_count(prevDayOrderItemsCount);
		daySalesPojo.setTotal_revenue(totalRevenue);
		daySalesService.add(daySalesPojo);
	}

//	@Scheduled(cron = "0/1 * * * * *")
//	public void testScheduler(){
//		System.out.println("this is scheduled task");
//	}
}
