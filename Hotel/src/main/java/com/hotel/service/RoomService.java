package com.hotel.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.mapper.RoomMapper;
import com.hotel.model.Pagination;
import com.hotel.model.PagingResponse;
import com.hotel.model.Room;
import com.hotel.model.SearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService{
	
	@Autowired
	private final RoomMapper Rmapper;
	
	@Transactional
	public void AddRoom(Room r) {
		Rmapper.AddRoom(r);
	}
	
	
	public Room RoomDetail(int room_num) {
		return Rmapper.RoomDetail(room_num);
	}
	
	
	public PagingResponse<Room> FindRoom(SearchDto params) {
		 int count = Rmapper.FindRoomcnt();
		 if (count < 1) {
		 return new PagingResponse<>(Collections.emptyList(), null);
		 }
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 List<Room> list = Rmapper.FindRoom(params);
		 return new PagingResponse<>(list, pagination);
		 }
	
	
	
	
	
}
