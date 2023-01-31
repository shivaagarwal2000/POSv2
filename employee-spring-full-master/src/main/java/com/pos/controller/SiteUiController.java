package com.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

	@RequestMapping(value = "/site/pricing")
	public ModelAndView pricing() {
		return mav("pricing.html");
	}

	@RequestMapping(value = "/site/features")
	public ModelAndView features() {
		return mav("features.html");
	}

	@RequestMapping(value = "/site/orderItem")
	public ModelAndView orderItem() {
		return mav("orderItem.html");
	}

	@RequestMapping(value = "/site/brandreport")
	public ModelAndView brandReport() {
		return mav("brandReport.html");
	}

	@RequestMapping(value = "/site/inventoryreport")
	public ModelAndView inventoryReport() {
		return mav("inventoryReport.html");
	}

	@RequestMapping(value = "/site/salesreport")
	public ModelAndView salesReport() {
		return mav("salesReport.html");
	}

}
