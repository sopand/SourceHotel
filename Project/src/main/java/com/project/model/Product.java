package com.project.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	private String p_name,p_category,p_nickname_m_fk,p_duedate,p_recruitdate,p_chk;
	private Timestamp p_createdate;
	private int p_id,p_price,p_sell;
	
	private List<MultipartFile> p_img,p_contentimg; // request 용
	private ArrayList<Img> img;
	private ArrayList<Option> option;
	private ArrayList<Discount> discount;
	private int[] p_discount_quan,p_discount_count; // request 용

}
