package com.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.model.OptionRoom;
import com.hotel.service.OptionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/option")
public class OptionController {
	
	@Autowired
	private final OptionService oPtService;
	
	@ResponseBody
	@PostMapping("/")
	public String optionadd(String[] option,Principal principal,int room_num) {
		String opt_id=principal.getName();
		
		for(String opt:option) {
			
			OptionRoom optional=OptionRoom.builder()
					.opt_name(opt)
					.opt_memberid(opt_id)
					.opt_roomnum(room_num)
					.build();					
			oPtService.AddOption(optional);
		}
		
		return null;
	}
}
