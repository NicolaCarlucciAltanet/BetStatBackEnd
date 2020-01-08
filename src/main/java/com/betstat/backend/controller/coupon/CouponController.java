package com.betstat.backend.controller.coupon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon/controller")
public class CouponController {

	@Value("${" + "coupon.data.name" + "}")
	private String name;

	@GetMapping(value = "/readcoupon")
	public void name() {
		System.out.println(name);
	}

}
