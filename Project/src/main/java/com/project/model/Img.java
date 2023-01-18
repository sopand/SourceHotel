package com.project.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Img {
	private String img_name,img_origname,img_keyword;
	private int img_pid_p_fk,img_id;
	

}
