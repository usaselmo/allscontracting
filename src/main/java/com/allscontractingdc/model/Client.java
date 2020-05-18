package com.allscontractingdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Entity<String>{

	private static final long serialVersionUID = -5611954855280867938L;
	private String id;
	private String name;
	private String address;
	private String cellPhone;
	private String phone;
	private String email;
	
}
