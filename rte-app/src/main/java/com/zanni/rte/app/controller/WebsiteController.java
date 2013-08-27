package com.zanni.rte.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebsiteController {
	@RequestMapping("/equalizer")
	public String equalizer(){
		return "equalizer";
	}
	
	@RequestMapping("/basic")
	public String basic(){
		return "basic";
	}
}
