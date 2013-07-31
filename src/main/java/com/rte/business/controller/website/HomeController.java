package com.rte.business.controller.website;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chart")
public class HomeController {
	@RequestMapping
	public String index() {
		return "index";
	}
}
