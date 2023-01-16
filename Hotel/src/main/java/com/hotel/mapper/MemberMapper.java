package com.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hotel.model.Member;

@Mapper
public interface MemberMapper {
	
	public void MemberAdd(Member m);
	
	public Member FindID(String m_id);
	
	
	
}
