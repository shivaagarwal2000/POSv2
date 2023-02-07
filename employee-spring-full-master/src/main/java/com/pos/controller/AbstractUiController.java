package com.pos.controller;

import com.pos.pojo.UserPojo;
import org.commons.util.ApiException;
import com.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.pos.model.InfoData;
import com.pos.util.SecurityUtil;
import com.pos.util.UserPrincipal;

import java.util.Objects;

@Controller
public abstract class AbstractUiController {

	@Autowired
	private InfoData info;

	@Autowired
	private UserService userService;

	@Value("${app.baseUrl}")
	private String baseUrl;

//	private String role;

	protected ModelAndView mav(String page) {
		// Get current user
		UserPrincipal principal = SecurityUtil.getPrincipal();

		info.setEmail(principal == null ? "" : principal.getEmail());

		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		if (Objects.isNull(principal) == false){
			String userEmail = principal.getEmail();
			try {
				UserPojo userPojo = userService.get(userEmail);
				mav.addObject("role",userPojo.getRole());
			}
			catch (ApiException apiException){
				System.out.println("unable to get the user");
			}
		}
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

}
