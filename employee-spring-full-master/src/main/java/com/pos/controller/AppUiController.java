package com.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/employee")
    public ModelAndView employee() {
        return mav("employee.html");
    }

    @RequestMapping(value = "/ui/admin")
    public ModelAndView admin() {
        return mav("user.html");
    }

    @RequestMapping(value = "/ui/brand")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/product")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/ui/brandreport")
    public ModelAndView brandReport() {
        return mav("brandReport.html");
    }

    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/orders")
    public ModelAndView orders() {
        return mav("orders.html");
    }
    @RequestMapping(value = "/ui/placeOrder")
    public ModelAndView createOrder() {
        return mav("placeOrder.html");
    }
    @RequestMapping(value = "/ui/placedOrderDetail/{id}")
    public ModelAndView placedOrderDetail() {
        return mav("orderDetail.html");
    }
    @RequestMapping(value = "/ui/pendingOrderDetail/{id}")
    public ModelAndView orderDetail() {
        return mav("editOrder.html");
    }
}
