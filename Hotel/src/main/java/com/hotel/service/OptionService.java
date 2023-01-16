package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.mapper.OptionMapper;
import com.hotel.model.OptionRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionService {
	
	@Autowired
	private final OptionMapper oMapper;
	
	public void AddOption(OptionRoom o) {
		
		oMapper.AddOption(o);
	}
}
