package com.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
		
	private String m_id,m_pwd,m_name,m_tel,m_address,m_grade,m_email,m_type;
	

}
