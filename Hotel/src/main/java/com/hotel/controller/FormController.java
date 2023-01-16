package com.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

	@GetMapping("/room")
	public String addform() {
		return "addroom";
	}
	@GetMapping("/index")
	public String index() {
		
		return "index";
	}
	@GetMapping("/users")
	public String loginform() {
		return "login";
	}
	
	@GetMapping("/reservation")
	public String reservationform() {
		return "reservation";
	}
	
	@GetMapping("/modal")
	public String modalform() {
		return "modal";
	}
}
