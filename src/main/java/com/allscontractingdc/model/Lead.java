package com.allscontractingdc.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lead implements Entity<String>{

	private static final long serialVersionUID = 2718925984228018742L;
	private String id;
	private Vendor vendor;
	private Date date;
	private String description;
	private BigDecimal fee;
	private String type;
	private Client client;
	
	public enum Vendor{
		HOME_ADVISOR, NETWORX
	}
	
	public static void main(String[] args) throws IOException {
		String str = new ObjectMapper().writeValueAsString(Lead.builder().date(new Date()).build());
		System.out.println(new ObjectMapper().readValue(str, Lead.class));
	}
}
