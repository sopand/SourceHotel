package com.hotel.model;

import lombok.Data;

@Data
public class Room {
	
	private int room_num,room_price,room_person;
	private String room_location,room_img,room_name,room_type,room_origimg;
}
