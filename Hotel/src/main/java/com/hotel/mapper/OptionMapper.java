package com.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hotel.model.OptionRoom;

@Mapper
public interface OptionMapper {

	void AddOption(OptionRoom o);
}
