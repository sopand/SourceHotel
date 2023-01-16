package com.hotel.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
	private int res_num;
	private String res_roomnum,res_adult,res_child,res_baby,res_city;
	private Date res_checkin,res_checkout;
	private OptionRoom res_opt;
}
