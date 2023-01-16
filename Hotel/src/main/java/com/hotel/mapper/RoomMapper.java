package com.hotel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hotel.model.Room;
import com.hotel.model.SearchDto;

@Mapper
public interface RoomMapper {

	void AddRoom(Room r);
	List<Room> FindRoom(SearchDto params);
	Room RoomDetail(int room_num);
	
	
	int FindRoomcnt();
}
