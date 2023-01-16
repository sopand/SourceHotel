package com.hotel.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionRoom {
	
	private int opt_roomnum;
	private String opt_memberid,opt_name;
	private Date opt_date;
}
