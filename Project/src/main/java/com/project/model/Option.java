package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Option {
	private int opt_pid_p_fk,opt_id;
	private String opt_option1,opt_option2,opt_quantity;

}
