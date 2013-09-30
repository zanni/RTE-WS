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
	
	@RequestMapping("/sync")
	public String sync(){
		return "sync";
	}
	
	@RequestMapping("/async")
	public String async(){
		return "async";
	}
	
	@RequestMapping("/drill")
	public String drill(){
		return "drill";
	}
}
